package com.example.backend.Model.Poll.Vote;
import lombok.Data;
import java.time.Instant;

import com.example.backend.Model.Poll.Poll;
import com.example.backend.Model.Poll.VoteOption;
import com.example.backend.Model.Poll.User.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;



@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "voteId")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long voteId; 
    Instant publishedAt = Instant.now(); 
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    User voter;
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne()
    Poll poll;
    @ManyToOne()
    @JsonIdentityReference(alwaysAsId = true) 
    VoteOption votesOn;
    
    public void votesOn(){
        
    }
    
}

