package com.example.backend.Model.User;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.Model.Poll.Poll;
import com.example.backend.Model.Poll.VoteOption;
import com.example.backend.Model.Vote.Vote;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username")
    private String username;
    private String email;
    @OneToMany(mappedBy = "createdBy", orphanRemoval = true)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Poll> polls = new ArrayList<>();
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "voter", orphanRemoval = true)
    private List<Vote> votes = new ArrayList<>();

    public User(String userName, String email) {
        this.username = userName;
        this.email = email;
        this.polls = new ArrayList<>();
        this.votes = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    public void addPolls(Poll poll) {
        polls.add(poll);

    }

    public Poll addPoll(String question) {
        Poll poll = new Poll(question);
        polls.add(poll);
        poll.setCreatedBy(this);
        return poll;

    }

    public Poll createPoll(String question) {
        Poll poll = new Poll();
        poll.setQuestion(question);
        poll.setCreatedBy(this);
        return poll;

    }

    public void addVote(Vote vote) {
        this.votes.add(vote);
        vote.setVoter(this);
    }

    public Vote voteFor(VoteOption voteOption) {
        Vote vote = new Vote();
        vote.setVotesOn(voteOption);
        addVote(vote);
        voteOption.getPoll().addVote(vote);

        return vote;
    }

    public void remoteVote(Vote vote) {
        this.votes.remove(vote);
    }

    public boolean hasVoted(Integer pollId) {
        boolean hasVoted = false;
        for (Vote vote : votes) {
            if (vote.getVoter().equals(this)) {
                hasVoted = true;
            }
            ;

        }
        return hasVoted;

    }
}
