package com.example.backend.Model.Poll;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PollQuestion {
    private List<PollOption> pollOptions; 
    private String questionText;
    private Integer optionIdCounter = 0; 
    private Integer questionID;  

   
    public void removeOption(PollOption option){
        pollOptions.remove(option); 
    }

    public void setPollOptions(List<PollOption> options){
            for (PollOption option : options) {
            optionIdCounter++;
            option.setOptionId(optionIdCounter);
        } 

    }

    public void addOption(PollOption option) {
        optionIdCounter++;
        option.setOptionId(optionIdCounter);
        pollOptions.add(option);
    }


    
}
