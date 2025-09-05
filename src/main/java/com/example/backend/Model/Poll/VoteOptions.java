package com.example.backend.Model.Poll;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoteOptions {
    private List<VoteOption> pollOptions; 
    @JsonIgnore
    private Integer optionIdCounter = 0; 

   
    public void removeOption(VoteOption option){
        pollOptions.remove(option); 
    }

    public void setPollOptions(List<VoteOption> options){
        this.pollOptions = options;
        for (VoteOption option : options) {
        optionIdCounter++;
        option.setOptionId(optionIdCounter);
        } 

    }

    public void addOption(VoteOption option) {
        optionIdCounter++;
        option.setOptionId(optionIdCounter);
        pollOptions.add(option);
    }


    
}
