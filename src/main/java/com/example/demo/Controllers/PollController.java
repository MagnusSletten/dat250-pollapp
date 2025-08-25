package com.example.demo.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Poll.Poll;
import com.example.demo.Model.Poll.PollOption;
import com.example.demo.Model.Poll.PollQuestion;

@RestController
@RequestMapping("/polls")
public class PollController {
    	List<Poll> polls; 


        public PollController {
            this.polls = new ArrayList();

        }

        @PostMapping("/{questions")
        public void vote(@RequestBody PollQuestion question){ 
            questions.add(question); 
        }

        @PostMapping("/questions/{question_number}")
        public String addQuestionOption(@RequestBody PollOption option, @PathVariable Integer question_number ){ 
            try {
            questions.get(question_number).addOption(option);
            return "Succesfully added option"+ option.getOptionName();
            }
            catch(Exception e) {
                return "Error adding optionumber";

            } 
            
        }

        @PostMapping("/remove-question-option")
        public void vote(@RequestBody PollOption option, @RequestBody Integer question_number){ 
            questions.get(question_number).addOption(option);; 
        }


        @PostMapping("/vote")
        public String vote(@RequestBody PollOption vote){
            poll.Vote(vote);
            return "You voted for "+vote.getOptionName();
        }
        @GetMapping("/poll-options")
        public List<String> getPollOptionNames() {
            return poll.getOptionsNames(); 
        }
        @GetMapping("/winner")
        public String getWinner() {
            try {
                return poll.getWinner().getOptionName(); 
            }
            catch (IllegalStateException e) {
                return "No votes have been cast";

            }
            
        }
}
