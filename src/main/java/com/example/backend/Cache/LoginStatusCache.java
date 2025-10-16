package com.example.backend.Cache;

import com.example.backend.Model.User.User;

import redis.clients.jedis.UnifiedJedis;

public class LoginStatusCache {
    UnifiedJedis jedis;
    private final String login_status = "login_status";

    public LoginStatusCache(UnifiedJedis jedis) {
        this.jedis = jedis;

    }

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
