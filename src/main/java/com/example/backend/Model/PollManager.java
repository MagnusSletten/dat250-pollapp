package com.example.backend.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.backend.Model.Poll.Poll;
import com.example.backend.Model.Poll.PollRequest;
import com.example.backend.Model.Poll.Vote.Vote;
import com.example.backend.Model.Poll.Vote.VoteRequest;
import com.example.backend.Repositories.PollRepository;
import com.example.backend.Repositories.UserRepository;
import com.example.backend.Repositories.VoteRepository;

import lombok.Data;
import lombok.NoArgsConstructor;


@Component
@Data

public class PollManager {
    private final PollRepository pollRepo;
    private final VoteRepository voteRepo;
    private final UserRepository userRepo;
            
    public Poll addPollFromRequest(PollRequest pollRequest){
        Optional<User> userOpt = userRepo.findByUsername(pollRequest.getCreator());
        if(userOpt.isPresent()){
        User user = userOpt.get(); 
        Poll poll = pollRequest.toPoll(user);

        user.addPolls(poll);
        pollRepo.save(poll);
        return poll; 
        } 
        return new Poll();     
        
    }

    public PollManager(PollRepository pollRepository, VoteRepository voteRepository, UserRepository userRepository){
        this.pollRepo = pollRepository; 
        this.voteRepo = voteRepository; 
        this.userRepo = userRepository; 

    }

    public Optional<Poll> getPoll(Integer poll_id){
       return pollRepo.findById(poll_id);
    }

    public Optional<User> getUser(String userName){
        return userRepo.findByUsername(userName);
        
    }

    public Collection<User> getUsers(){
        return userRepo.findAll(); 
    }

    public boolean deletePoll(Integer pollId){
        if (pollRepo.existsById(pollId)){
            pollRepo.deleteById(pollId);
        }
        
        return false;   

    }

    public User addUserFromRequest(UserRequest userRequest) throws Exception{
      User user = userRequest.toUser();
      String userName = userRequest.getUsername(); 
      userRepo.save(user);
      return user; 
    }

    public List<Vote> getVotes(Integer pollID){
        return voteRepo.findByPollId(pollID);
        
    }

    public Map<Integer, Long> getVoteResults(Integer pollID){
           return pollRepo.findById(pollID).get().countVotesByPresentationOrder();   
        
        
    }
public Vote addVoteWithUser(VoteRequest request){
    User user = userRepo.findByUsername(request.getUserName()).get();
    Integer id = request.getPollId(); 
    Poll poll = getPoll(id).get();
    Vote vote = request.toVote(user, poll);
    if (!user.hasVoted(poll.getId())) {
        poll.addVote(vote);
        user.addVote(vote);
    } else {
        poll.changeVote(vote);
    }
    return vote;
}

public Vote addVoteAnonymous(VoteRequest request) {
    Poll poll = getPoll(request.getPollId()).get(); 
    Vote vote = request.toVoteAnonymous(poll);
 //   vote.setVoteId(voteId++);
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



