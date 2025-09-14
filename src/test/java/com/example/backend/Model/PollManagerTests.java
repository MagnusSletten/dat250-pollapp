package com.example.backend.Model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.backend.Model.Poll.Poll;
import com.example.backend.Model.Poll.PollRequest;
import com.example.backend.Model.Poll.VoteOption;
import com.example.backend.Model.Poll.Vote.VoteRequest;

public class PollManagerTests {
    PollManager manager; 

    void setUp(){
        manager = new PollManager(); 

    }

    void PollSetUp(){
    manager = new PollManager();
    UserRequest user = new UserRequest(); 
    user.setUserName("Peter");
    
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
        PollSetUp();

        VoteRequest vote = new VoteRequest();
        vote.setPollId(1);
        vote.setPresentationOrder(1);
        assert(manager.getVotes(1).size() == 0);
        manager.addVoteAnonymous(vote);
        assert(manager.getVotes(1).size() == 1);
        manager.addVoteAnonymous(vote);
        assert(manager.getVotes(1).size() == 2); //Anonymous votes will increase
        



    }
    @Test
    void addVotesWithUser(){
        PollSetUp();

        VoteRequest vote = new VoteRequest();
        vote.setPollId(1);
        vote.setPresentationOrder(1);
        vote.setUserName("Peter");
        assert(manager.getVotes(1).size() == 0);
        manager.addVoteWithUser(vote);
        assert(manager.getVotes(1).size() == 1);
        manager.addVoteWithUser(vote);
        assert(manager.getVotes(1).size() == 1); //Should not change the number of votes
        



    }

    @Test
    void addVotesWithUserDeletePoll(){
        PollSetUp();

        VoteRequest vote = new VoteRequest();
        vote.setPollId(1);
        vote.setPresentationOrder(1);
        vote.setUserName("Peter");
        assert(manager.getVotes(1).size() == 0);
        manager.addVoteWithUser(vote);
        assert(manager.getVotes(1).size() == 1);
        assert(manager.getUser("Peter").getVotes().size() == 1);
        manager.deletePoll(1);
        assert(manager.getUser("Peter").getVotes().size() == 0);        


    }

    @Test
    void changeVote(){
        PollSetUp();

        VoteRequest vote = new VoteRequest();
        vote.setPollId(1);
        vote.setPresentationOrder(1);
        vote.setUserName("Peter");
        assert(manager.getVotes(1).size() == 0);
        manager.addVoteWithUser(vote);
        assert(manager.getVotes(1).size() == 1);
        vote.setPresentationOrder(2);
        manager.addVoteWithUser(vote);
        assert(manager.getVotes(1).size() == 1); //Should not change when user changes vote


    }
}
