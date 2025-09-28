package com.example.backend.Model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.backend.Model.Poll.Poll;
import com.example.backend.Model.Poll.PollRequest;
import com.example.backend.Model.Poll.VoteOption;
import com.example.backend.Model.Poll.User.UserRequest;
import com.example.backend.Model.Poll.Vote.VoteRequest;
import com.example.backend.Repositories.PollRepository;
import com.example.backend.Repositories.UserRepository;
import com.example.backend.Repositories.VoteRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE) 
public class PollManagerTests {
    @Autowired UserRepository userRepo;
    @Autowired PollRepository pollRepo;
    @Autowired VoteRepository voteRepo;
    @Autowired PollManager manager;
    @PersistenceContext
    EntityManager em;

    @BeforeEach
    @Transactional
    void PollSetUp(){
    voteRepo.deleteAll();
    pollRepo.deleteAll();        
    userRepo.deleteAll();
    UserRequest user = new UserRequest(); 
    user.setUsername("Peter");
    
    try{
    manager.addUserFromRequest(user);
    }
    catch(Exception e){
        
    }

    PollRequest poll = new PollRequest();
    poll.setCreator("Peter");
    VoteOption yesOption = new VoteOption();
    yesOption.setCaption("yes");
    VoteOption noOption = new VoteOption();
    noOption.setCaption("no");
    ArrayList<VoteOption> votelList = new ArrayList<>();
    votelList.add(yesOption);
    votelList.add(noOption);
    poll.setVoteOptions(votelList);
    manager.addPollFromRequest(poll);
    

    }

    @Test
    void addVotesWithoutUser(){
        Integer pollId = manager.getLatestPollId().get(); 
        VoteRequest vote = new VoteRequest();
        vote.setPollId(pollId);
        vote.setPresentationOrder(1);
        assert(manager.getVotes(pollId).size() == 0);
        manager.addVoteAnonymous(vote);
        assert(manager.getVotes(pollId).size() == 1);
        manager.addVoteAnonymous(vote);
        assert(manager.getVotes(pollId).size() == 2); //Anonymous votes will increase
        



    }
    @Test
    void addVotesWithUser(){
        Integer pollId = manager.getLatestPollId().get(); 
        VoteRequest vote = new VoteRequest();
        vote.setPollId(pollId);
        vote.setPresentationOrder(1);
        vote.setUserName("Peter");

        assertEquals(0, manager.getVotes(pollId).size());
        manager.addVoteWithUser(vote);
        assertEquals(1, manager.getVotes(pollId).size());
        manager.addVoteWithUser(vote);
        assertEquals(1, manager.getVotes(pollId).size()); //Should not change the number of votes
        



    }

    @Test
    @Transactional
    void addVotesWithUserDeletePoll(){
        Integer pollId = manager.getLatestPollId().get(); 
        VoteRequest vote = new VoteRequest();
        vote.setPollId(pollId);
        vote.setPresentationOrder(1);
        vote.setUserName("Peter");
        assert(manager.getVotes(pollId).size() == 0);
        manager.addVoteWithUser(vote);
        assert(manager.getVotes(pollId).size() == 1);
        assertEquals(1,manager.getUser("Peter").get().getVotes().size());
        manager.deletePoll(pollId);
        em.flush();
        em.clear();
        assertEquals(0,manager.getUser("Peter").get().getVotes().size());        

    }

    @Test
    void changeVote(){
        Integer pollId = manager.getLatestPollId().get();
        VoteRequest vote = new VoteRequest();
        vote.setPollId(pollId);
        vote.setPresentationOrder(1);
        vote.setUserName("Peter");
        assert(manager.getVotes(pollId).size() == 0);
        manager.addVoteWithUser(vote);
        assert(manager.getVotes(pollId).size() == 1);
        vote.setPresentationOrder(2);
        manager.addVoteWithUser(vote);
        assert(manager.getVotes(pollId).size() == 1); //Should not change when user changes vote


    }
}
