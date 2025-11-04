package com.example.backend.Cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.backend.Model.Poll.Poll;

import redis.clients.jedis.UnifiedJedis;

@Component
public class RedisVoteCache implements VoteCache {
    String host = System.getenv().getOrDefault("REDIS_HOST", "localhost");
    String port = System.getenv().getOrDefault("REDIS_PORT", "6379");
    UnifiedJedis jedis = new UnifiedJedis("redis://" + host + ":" + port);

    public void setVotes(Poll poll) {
        Map<String, String> votes = poll.countVotesByPresentationOrderString();
        if (votes == null || votes.isEmpty()) {
            return;
        }
        jedis.hset(poll.getId().toString(), votes);
        jedis.expire(poll.getId().toString(), 20 * 60);
    }
    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        jedis.flushDB();
        System.out.println("Redis cleared on startup.");
    }

    public RedisVoteCache() {
    }

    public RedisVoteCache(UnifiedJedis jedis) {
        this.jedis = jedis;
    }

    public Map<Integer, Integer> getVoteResults(Poll poll) {
        Map<String, String> votes = jedis.hgetAll(poll.getId().toString());
        Map<Integer, Integer> result = new HashMap<>();
        for (Map.Entry<String, String> entry : votes.entrySet()) {
            result.put(Integer.parseInt(entry.getKey()), Integer.parseInt(entry.getValue()));
        }
        return result;

    }

    public void removeVotes(Poll poll) {
        jedis.del(poll.getId().toString());
    }

    public boolean isCached(Poll poll) {
        return jedis.exists(poll.getId().toString());
    }
}
