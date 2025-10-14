package com.example.backend.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;


import org.springframework.stereotype.Component;

import com.example.backend.Cache.VoteCache;
import com.example.backend.Model.Poll.Poll;
import com.example.backend.Model.Poll.PollRequest;
import com.example.backend.Model.User.User;
import com.example.backend.Model.User.UserRequest;
import com.example.backend.Model.Vote.Vote;
import com.example.backend.Model.Vote.VoteRequest;
import com.example.backend.Repositories.PollRepository;
import com.example.backend.Repositories.UserRepository;
import com.example.backend.Repositories.VoteRepository;

import jakarta.transaction.Transactional;
import lombok.Data;


@Component
@Data
public class PollManager {
    private final PollRepository pollRepo;
    private final VoteRepository voteRepo;
    private final UserRepository userRepo;
    private final VoteCache voteCache;
    
    @Transactional        
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

    @Transactional
    public Optional<Poll> getPoll(Integer poll_id){
        return pollRepo.findById(poll_id);
    }
    @Transactional
    public Optional<User> getUser(String userName){
        return userRepo.findByUsername(userName);
        
    }
    @Transactional
    public Collection<User> getUsers(){
        return userRepo.findAll(); 
    }
    @Transactional
    public boolean deletePoll(Integer pollId){
        if (pollRepo.existsById(pollId)){
            voteRepo.deleteByPollId(pollId);
            pollRepo.deleteById(pollId);
        }
        
        return false;   

    }
    @Transactional
    public User addUserFromRequest(UserRequest userRequest) throws Exception{
        User user = userRequest.toUser();
        userRepo.save(user);
        return user; 
    }

    public List<Vote> getVotes(Integer pollID){
        return voteRepo.findByPollId(pollID);
        }

            
        
    @Transactional
    public Map<Integer, Integer> getVoteResults(Integer pollID){
        Poll poll = getPoll(pollID).get();
        if(voteCache.isCached(poll)){
        System.out.println("Poll is already cached. returning from cache");
        return voteCache.getVoteResults(poll);
        }
        
        else{ 
        voteCache.setVotes(poll);
        System.out.println("Poll not cached, querying DB and caching result");
        return pollRepo.findById(poll.getId()).get().countVotesByPresentationOrder();   
        }
        
        
    }
    @Transactional()
    public Optional<Integer> getLatestPollId() {
        return pollRepo.findTopByOrderByIdDesc()
                    .map(Poll::getId);
    }
    @Transactional
    public Vote addVoteWithUser(VoteRequest request){
        User user = userRepo.findByUsername(request.getUserName()).get();
        Integer id = request.getPollId(); 
        Poll poll = getPoll(id).get();
        if(voteCache.isCached(poll)){
        voteCache.removeVotes(poll);
        }
        Vote vote = request.toVote(user, poll);
        if (!voteRepo.existsByPollIdAndVoterId(id,user.getId())) {
            poll.addVote(vote);
            user.addVote(vote);

            voteRepo.save(vote);
        } else {
            Vote vote2 = voteRepo.findByPollIdAndVoterId(id, user.getId());
            vote2.setVotesOn(vote.getVotesOn());
            poll.changeVote(vote2);
            user.addVote(vote2);
        
        }
        return vote;
    }
    @Transactional
    public Vote addVoteAnonymous(VoteRequest request) {
        Poll poll = getPoll(request.getPollId()).get();
        if(voteCache.isCached(poll)){
        voteCache.removeVotes(poll);
        } 
        Vote vote = request.toVoteAnonymous(poll);
        poll.addVote(vote);
        return vote;
    }
        
    @Transactional
    public void removeVote(Integer pollID, Integer userId){
        getVotes(pollID).remove((Vote) getUserVote(pollID, userId));


    }
    @Transactional
    public Vote getUserVote(Integer pollID, Integer userId){
        for(Vote vote : this.getVotes(pollID)){
            if(vote.getVoteId().equals(userId)){
                return vote; 
            }

        }
        throw new NoSuchElementException("This pollID not found in votes");
}

    



}



