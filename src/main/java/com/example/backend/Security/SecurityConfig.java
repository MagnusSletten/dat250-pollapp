package com.example.backend.Security;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.backend.Model.User.User;
import com.example.backend.Repositories.UserRepository;

import jakarta.servlet.http.HttpServletResponse;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

@Bean
SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    var repo = CookieCsrfTokenRepository.withHttpOnlyFalse();
    repo.setHeaderName("X-XSRF-TOKEN");
    repo.setCookieCustomizer(c -> c.path("/").sameSite("Lax")); 

    var requestHandler = new CsrfTokenRequestAttributeHandler();
    requestHandler.setCsrfRequestAttributeName("_csrf"); 

    return http
        .cors(Customizer.withDefaults())
        //csrf protection
        .csrf(csrf -> csrf
            .csrfTokenRepository(repo)
            .csrfTokenRequestHandler(requestHandler) 
            .ignoringRequestMatchers(
                request -> request.getRequestURI().startsWith("/users/auth/"),
                request -> request.getRequestURI().startsWith("/auth/csrf"),
                request -> request.getRequestURI().equals("/users") && "POST".equals(request.getMethod())
            )
        )
        //Session based security, related to logins
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers("/users/auth/**").permitAll()
            .requestMatchers("/auth/csrf").permitAll()
            .requestMatchers(HttpMethod.GET, "/polls/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/users").permitAll()
            .requestMatchers(HttpMethod.POST, "/polls/**").authenticated()
            
        )
        .formLogin(f -> f
            .loginProcessingUrl("/users/auth/login")
            .successHandler((req, res, auth) -> {
                CsrfToken token = (CsrfToken) req.getAttribute(CsrfToken.class.getName());
                if (token != null) {
                    res.setHeader(token.getHeaderName(), token.getToken());
                }
                res.setStatus(HttpServletResponse.SC_OK);
            })
            .failureHandler((req, res, ex) -> res.setStatus(401))
            .permitAll()
        )
        .build();
}

// Allow our frontend to read backend responses 
@Bean
CorsConfigurationSource corsConfigurationSource() {
  var cfg = new CorsConfiguration();
  cfg.setAllowedOrigins(List.of("http://localhost:5173")); 
  cfg.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
  cfg.setAllowedHeaders(List.of("Content-Type","X-XSRF-TOKEN","Authorization","X-Requested-With"));
  cfg.setAllowCredentials(true);
  var source = new UrlBasedCorsConfigurationSource();
  source.registerCorsConfiguration("/**", cfg);
  return source;
}


// User validation
@Bean
public UserDetailsService userDetailsService(UserRepository userRepository) {
    return new UserDetailsService() {
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<User> maybeUser = userRepository.findByUsername(username);
            if (maybeUser.isEmpty()) {
                throw new UsernameNotFoundException("User does not exist");
            }
            return maybeUser.get();
        }
    };
}
    
@Bean
AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
    return cfg.getAuthenticationManager(); 
}
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}   

