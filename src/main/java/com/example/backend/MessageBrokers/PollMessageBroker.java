package com.example.backend.MessageBrokers;

import com.example.backend.Model.Vote.VoteRequest;

public interface PollMessageBroker {

    public void sendVote(Integer pollId, VoteRequest vote) throws Exception;

    public void recieve(Listener listener);
    
}
