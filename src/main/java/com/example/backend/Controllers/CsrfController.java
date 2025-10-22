package com.example.backend.Controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.web.csrf.CsrfToken;

@RestController
@RequestMapping("/auth")
public class CsrfController {
  @GetMapping("/csrf")
  public Map<String,String> csrf( CsrfToken token) {
    return Map.of("token", token.getToken()); 
  }
}