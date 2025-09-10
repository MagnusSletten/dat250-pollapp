package com.example.backend.Model.Poll;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.backend.Model.User;
import com.example.backend.Model.Poll.Vote.Vote;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,  property = "pollID")
public class Poll {
    private Integer pollID; 
    private String title;
    private String question;
    private ArrayList<VoteOption> voteOptions = new ArrayList<>();
    private Instant publishedAt;
    private Instant validUntil;
    @JsonIdentityReference(alwaysAsId = true)
    private List<Vote> votes = new ArrayList<>();

   @JsonIdentityReference(alwaysAsId = true)
    private User creator;

    
    
    public void setCreator(User creator){
        this.creator = creator;

    }

    public void addVote(Vote vote){
        this.votes.add(vote);
    }

    public void changeVote(Vote vote){
        votes.set(votes.indexOf(vote),vote); 
    }
    
    public void remoteVotes(){
        for(int i = votes.size()-1; i>=0; i-- ){
            Vote vote = votes.get(i);
            vote.getVoter().remoteVote(vote);
            votes.remove(i);
        }
    }
}
