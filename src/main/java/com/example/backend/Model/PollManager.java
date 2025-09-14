package com.example.backend.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import com.example.backend.Model.Poll.Poll;
import com.example.backend.Model.Poll.PollRequest;
import com.example.backend.Model.Poll.Vote.Vote;
import com.example.backend.Model.Poll.Vote.VoteRequest;

@Component
public class PollManager {
    HashMap<Integer,Poll> polls;
    HashMap<String,User> users;
    
    Integer maxPollId = 1;
    Integer maxUserId = 1;  
    Integer voteId = 1; 
            
    public PollManager() {
        this.polls = new HashMap<>();
        this.users = new HashMap<>(); 

    }

    public Poll addPollFromRequest(PollRequest pollRequest){
        User user = users.get(pollRequest.getCreator()); 
        Poll poll = pollRequest.toPoll(maxPollId, user);
        user.addPolls(poll);
        polls.put(maxPollId, poll);
        maxPollId++;     
        return poll; 
    }

    public Poll getPoll(Integer poll_id){
       return polls.get(poll_id);
    }

    public User getUser(String userName){
        return users.get(userName);
        
    }

    public Collection<User> getUsers(){
        return users.values(); 
    }

    public boolean deletePoll(Integer pollId){
        if (polls.keySet().contains(pollId)){
            polls.get(pollId).remoteVotes();
            polls.remove(pollId);
            return true;
        }
        return false;   

    }

    public User addUserFromRequest(UserRequest userRequest) throws Exception{
      User user = userRequest.toUser();
      String userName = userRequest.getUserName(); 
      users.put(userName, user);
      return user; 
    }

    public List<Vote> getVotes(Integer pollID){
         return polls.get(pollID).getVotes(); 
         
    }

    public Map<Integer, Long> getVoteResults(Integer pollID){
        return polls.get(pollID).countVotesByPresentationOrder();
    }
public Vote addVoteWithUser(VoteRequest request){
    User user = users.get(request.getUserName());
    Integer id = request.getPollId(); 
    Poll poll = polls.get(request.getPollId());
    Vote vote = request.toVote(user, poll);
    vote.setVoteId(voteId++);
    if (!user.hasVoted(poll.getPollID())) {
        poll.addVote(vote);
        user.addVote(vote);
    } else {
        poll.changeVote(vote);
    }
    return vote;
}

public Vote addVoteAnonymous(VoteRequest request) {
    Poll poll = polls.get(request.getPollId());
    Vote vote = request.toVoteAnonymous(poll);
    vote.setVoteId(voteId++);
    poll.addVote(vote);
    return vote;
}
    

public void removeVote(Integer pollID, Integer userId){
    getVotes(pollID).remove((Vote) getUserVote(pollID, userId));


}

    public Vote getUserVote(Integer pollID, Integer userId){
        for(Vote vote : this.getVotes(pollID)){
            if(vote.getVoteId().equals(userId)){
                return vote; 
            }

        }
       throw new NoSuchElementException("This pollID not found in votes");
    }

    



}



