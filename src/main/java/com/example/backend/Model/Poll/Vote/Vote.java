package com.example.backend.Model.Poll.Vote;
import lombok.Data;
import java.time.Instant;

import org.hibernate.annotations.ManyToAny;

import com.example.backend.Model.User;
import com.example.backend.Model.Poll.Poll;
import com.example.backend.Model.Poll.VoteOption;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;



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
    @ManyToOne
    Poll poll;
    @ManyToOne 
    VoteOption votesOn;
    
    public void votesOn(){
        
    }
    
}

