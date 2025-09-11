package com.example.backend.Model.Poll;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.backend.Model.User;
import com.example.backend.Model.Poll.Vote.Vote;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
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

    public void changeVote(Vote vote) {
        for (int i = 0; i < votes.size(); i++) {
            if (votes.get(i).getVoteId() == vote.getVoteId()) {
                votes.set(i, vote);
                return;
            }
        }
        throw new IllegalArgumentException("Vote with id " + vote.getVoteId() + " not found");
    }
    
    public Map<Integer, Long> countVotesByPresentationOrder() {
    Map<Integer, Long> counts = new HashMap<>();

    for (Vote vote : votes) {
        counts.put(vote.getOption().getPresentationOrder(),
                   counts.getOrDefault(vote.getOption().getPresentationOrder(), 0L) + 1);
    }

    return counts;
}
    public void remoteVotes(){
        for(int i = votes.size()-1; i>=0; i-- ){
            Vote vote = votes.get(i);
            vote.getVoter().remoteVote(vote);
            votes.remove(i);
        }
    }
}
