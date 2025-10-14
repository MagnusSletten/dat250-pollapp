package com.example.backend.Cache;

import java.util.Map;

import com.example.backend.Model.Poll.Poll;

import redis.clients.jedis.UnifiedJedis;

public interface VoteCache {

    public void setVotes(Poll poll);

    public Map<Integer,Integer> getVoteResults(Poll poll);

    public void removeVotes(Poll poll);

    public boolean isCached(Poll poll); 
        
}

    

    
