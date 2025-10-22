package com.example.backend.Cache;

import org.springframework.stereotype.Component;

import com.example.backend.Model.User.User;

import lombok.AllArgsConstructor;
import redis.clients.jedis.UnifiedJedis;

@Component
@AllArgsConstructor
public class LoginStatusCache {
    private final String host = System.getenv().getOrDefault("REDIS_HOST", "localhost");
    private final String port = System.getenv().getOrDefault("REDIS_PORT", "6379");
    private final UnifiedJedis jedis = new UnifiedJedis("redis://" + host + ":" + port);
    private final String login_status = "login_status";

    

    public void logIn(User user) {
        jedis.sadd(login_status, user.getId().toString());
    }

    public void logOut(User user) {
        jedis.srem(login_status, user.getId().toString());
    }

    public boolean loggedIn(User user) {
        return jedis.sismember(login_status, user.getId().toString());
    }

}
