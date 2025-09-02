package com.example.backend.Controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.Model.Poll.Poll;
import com.example.backend.Model.Poll.PollQuestion;

@RestController
@CrossOrigin
@RequestMapping("/polls")
public class PollController {
    	HashMap<Integer,Poll> polls;
        Integer max_Id = 1;  


        public PollController() {
            this.polls = new HashMap<>(); 

        }

        @PostMapping("/")
        public String addPoll(@RequestBody Poll poll){ 
            try {
            polls.put(max_Id, poll);
            poll.setID(max_Id);
            max_Id ++; 
            return "Succesfully added poll with ID:"+ (max_Id -1);
            }
            catch(Exception e) {
                return "Error adding question";

            } 
            
        }

        @GetMapping("/{pollID}")
        public Poll getPoll(@PathVariable("pollID") Integer pollID) throws Exception{
            try {
            return polls.get(pollID);
            }
            catch(Exception e){
                throw e; 
            }
 
        }

        @PostMapping("/{poll-id}/questions/")
        public String addQuestion( @PathVariable Integer pollID, @RequestBody PollQuestion question){ 
            try {
            polls.get(pollID).addQuestion(question);
            return "Succesfully added question"+ question.getQuestion();
            }
            catch(Exception e) {
                return "Error adding question";

            } 
            
        }

        @GetMapping("/{poll-id}/questions/")
        public List<PollQuestion> getQuestionList(@PathVariable Integer pollID) {
            return polls.get(pollID).getQuestions(); 
        }



}
