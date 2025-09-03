package com.example.backend.Controllers;

import java.util.HashMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.Model.Poll.Poll;
import com.example.backend.Model.Poll.Vote;

@RestController
@RequestMapping("/vote")
public class VoteController {
    PollManager pollManager;

    public VoteController(PollManager pollManager){
        this.pollManager = pollManager; 
    }

    @PostMapping("/")
    public String addPoll(@RequestBody Vote vote){ 
        try {
        pollManager.addVote(vote.getPollId(),vote);  
        return "Succesfully voted with vote: "+vote.getOptionId();
        }
        catch(Exception e) {
            return "Error adding vote" + e;

        }    
    }

}
