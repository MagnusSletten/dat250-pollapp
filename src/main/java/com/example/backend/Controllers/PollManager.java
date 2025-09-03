package com.example.backend.Controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.Model.User;
import com.example.backend.Model.Poll.Poll;
import com.example.backend.Model.Poll.PollQuestion;

@Component
public class PollManager {
    HashMap<Integer,Poll> polls;
    HashMap<Integer,User> users;
    
    Integer maxPollId = 1;
    Integer maxUserId = 1;  
            
    public PollManager() {
        this.polls = new HashMap<>(); 

    }

    public void addPoll(Poll poll){ 
        polls.put(maxPollId, poll);
        poll.setID(maxPollId);
        maxPollId ++;
        
    }

    public Poll getPoll(Integer poll_id){
       return polls.get(poll_id);
    }

    public User getUser(Integer userID){
        return users.get(userID);
        
    }

    public void addUser(User user){
        maxUserId ++; 
        this.users.put(maxPollId, user);
    }


}



