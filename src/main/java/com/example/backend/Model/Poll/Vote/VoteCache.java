package com.example.backend.Model.Poll.Vote;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.backend.Model.Poll.Poll;

import redis.clients.jedis.UnifiedJedis;
@Component
public class VoteCache {
    	UnifiedJedis jedis= new UnifiedJedis("redis://localhost:6379");
        
        public void setVotes(Poll poll){
            jedis.hset(poll.getId().toString(), poll.countVotesByPresentationOrderString());
            jedis.expire(poll.getId().toString(),10);
        }

        public VoteCache(  ){
        }

        public VoteCache(  UnifiedJedis jedis){
            this.jedis = jedis; 
        }


        public Map<Integer,Integer> getVoteResults(Poll poll){
           Map<String,String>  votes = jedis.hgetAll(poll.getId().toString());
           Map<Integer, Integer> result = new HashMap<>();
            for (Map.Entry<String, String> entry : votes.entrySet()) {
            result.put(Integer.parseInt(entry.getKey()), Integer.parseInt(entry.getValue()));
                }
                return result;
        
        }

        public void removeVotes(Poll poll){
            jedis.del(poll.getId().toString());
        }

       public boolean isCached(Poll poll) {
            return jedis.exists(poll.getId().toString());
}
}
