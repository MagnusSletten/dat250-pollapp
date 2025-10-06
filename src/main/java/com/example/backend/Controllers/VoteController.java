package com.example.backend.Controllers;


import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.MessageBrokers.MyListener;
import com.example.backend.MessageBrokers.PollBroker;
import com.example.backend.MessageBrokers.PollReciever;
import com.example.backend.Model.PollManager;
import com.example.backend.Model.Poll.Vote.Vote;
import com.example.backend.Model.Poll.Vote.VoteRequest;

@RestController
@CrossOrigin
@RequestMapping("/polls")
public class VoteController implements MyListener, CommandLineRunner  {
    PollManager pollManager;
    PollBroker broker; 
    

    public VoteController(PollManager pollManager){
        this.pollManager = pollManager;
        this.broker = new PollBroker();  
    }

    @PostMapping("/{pollID}/votes")
    public VoteRequest addVote(@RequestBody VoteRequest voteRequest) throws Exception { 
            broker.sendVoteEvent(voteRequest.getPollId(), voteRequest);
            return voteRequest; 

    }
      
    @GetMapping("/{pollID}/votes")
    public List<Vote> getVotes(@PathVariable Integer pollID){
        return pollManager.getVotes(pollID);
    }
    @GetMapping("/{pollID}/votes/results")
    public Map<Integer,Integer> getVoteResults(@PathVariable Integer pollID){
        return pollManager.getVoteResults(pollID);
    }

    @Override
    public void onEvent(String message) {
    VoteRequest voteRequest = VoteRequest.fromJson(message); 
    if(voteRequest.hasUsername()){
            pollManager.addVoteWithUser(voteRequest); 
    }
    else{
            pollManager.addVoteAnonymous(voteRequest); 
    }
        
    }

    @Override
    public void run(String... args) throws Exception {
        PollReciever reciever = new PollReciever(); 
        reciever.voteReciever(this);
    }
}



