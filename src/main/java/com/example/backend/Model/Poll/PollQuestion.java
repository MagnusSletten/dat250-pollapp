package com.example.backend.Model.Poll;

import java.util.ArrayList;
import java.util.List;

public class PollQuestion {
    private List<PollOption> options; 
    private String question; 

    public PollQuestion(){
        this.options = new ArrayList<>();

    }

    public String getQuestion(){
        return question; 
    }
    public void setQuestion(String question){
        this.question = question; 
    }

    public void addOption(PollOption option){
        this.options.add(option); 
    }

    public List<PollOption> getPollOptions() {
        return this.options; 
    }

    public void setPollOptions(List<PollOption> options){
        this.options = options; 

    }

    public void removeOption(PollOption option){
        options.remove(option); 
    }
    
    
}
