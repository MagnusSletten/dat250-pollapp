package com.example.backend.Model.Poll;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import lombok.Data;

@Data
public class Poll {
    private Integer pollID; 
    private String title;
    private String question;
    private ArrayList<VoteOption> voteOptions; 
    private Instant publishedAt = Instant.now(); 
    private Instant validUntil = Instant.now().plus(7, ChronoUnit.DAYS);

    
}
