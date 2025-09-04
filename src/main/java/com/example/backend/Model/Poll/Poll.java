package com.example.backend.Model.Poll;

import lombok.Data;

@Data
public class Poll {
    private Integer pollID; 
    private String title; 
    private PollQuestion question; 
    
}
