package com.example.backend.Controllers;


import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.Model.PollManager;
import com.example.backend.Model.Poll.Vote.Vote;
import com.example.backend.Model.Poll.Vote.VoteRequest;

@RestController
@CrossOrigin
@RequestMapping("/polls")
public class VoteController {
    PollManager pollManager;

    public VoteController(PollManager pollManager){
        this.pollManager = pollManager; 
    }

    @PostMapping("/{pollID}/votes")
    public Vote addVote(@RequestBody VoteRequest voteRequest){ 
        if(voteRequest.hasUsername()){
            return pollManager.addVoteWithUser(voteRequest); 
        }
        else{
            return pollManager.addVoteAnonymous(voteRequest); 
        }
       

    }   
        @GetMapping("/{pollID}/votes")
        public List<Vote> getVotes(@PathVariable Integer pollID){
            return pollManager.getVotes(pollID);
        }
        @GetMapping("/{pollID}/votes/results")
        public Map<Integer,Long> getVoteResults(@PathVariable Integer pollID){
            return pollManager.getPoll(pollID).countVotesByPresentationOrder();
        }
}



