package com.example.demo.Model.Poll;

import java.util.ArrayList;
import java.util.List;

public class PollQuestion {
    private List<PollOption> options; 
    private String question; 

    public PollQuestion(){
        this.options = new ArrayList<>();

    }

    public void setQuestion(String question){
        this.question = question; 
    }

    public void addOption(PollOption option){
        this.options.add(option); 
    }

    public void removeOption(PollOption option){
        options.remove(option); 
    }
    
    
}
