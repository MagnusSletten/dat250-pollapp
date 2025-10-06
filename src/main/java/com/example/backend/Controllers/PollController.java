package com.example.backend.Controllers;

import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.MessageBrokers.PollBroker;
import com.example.backend.Model.PollManager;
import com.example.backend.Model.Poll.Poll;
import com.example.backend.Model.Poll.PollRequest;


@RestController
@CrossOrigin
@RequestMapping("/polls")
public class PollController {
    	HashMap<Integer,Poll> polls;
        PollManager manager;
         
        public PollController(PollManager manager) {
            this.polls = new HashMap<>();
            this.manager = manager;  
        }

        @PostMapping
        public Poll addPoll(@RequestBody PollRequest pollRequest){ 
            Poll poll = manager.addPollFromRequest(pollRequest);
            return poll; 
        }

        @DeleteMapping("/{pollID}")
        public boolean deletePoll(@PathVariable("pollID") Integer pollID){
            return manager.deletePoll(pollID);
 
        }

        @GetMapping("/{pollID}")
        public Poll getPoll(@PathVariable("pollID") Integer pollID) throws Exception{
            try {
            return manager.getPoll(pollID).get();
            }
            catch(Exception e){
                throw e; 
            }
 
        }

}