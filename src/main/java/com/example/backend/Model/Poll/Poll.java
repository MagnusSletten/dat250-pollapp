package com.example.backend.Model.Poll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.backend.Model.Poll.PollOption;

@Component
public class Poll {
    private Integer pollID; 
    private String title; 
    private List<PollQuestion> questions;

    
    public Poll(){
        this.questions = new ArrayList<>(); 
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title; 
    }

    public Integer getID(){
        return pollID; 
    }

    public void setID(Integer id){
        this.pollID = id; 
    }

    public List<PollQuestion> getQuestions(){
        return questions; 
    }

    public void addQuestion(PollQuestion question){
        questions.add(question);
        
    }

    public void setQuestions(List<PollQuestion> questions){
        this.questions = questions; 
    }

    public void removeQuestion(int questionNumber){
        questions.remove((int) questionNumber);
          
    }
    
}
