package com.example.backend.Model.Poll;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PollQuestion {
    private List<PollOption> pollOptions; 
    private String questionText;
    @JsonIgnore
    private Integer optionIdCounter = 0; 

   
    public void removeOption(PollOption option){
        pollOptions.remove(option); 
    }

    public void setPollOptions(List<PollOption> options){
        this.pollOptions = options;
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
