package com.example.demo.Model.Poll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.demo.Model.Poll.PollOption;


public class Poll {
    private Integer pollID; 
    private List<PollOption> options;
    private HashMap<PollOption, Integer> votes; 
    
    public Poll(){
        this.options = new ArrayList<>();
        this.votes = new HashMap<>();
    }

    public Integer getID(){
        return pollID; 
    }

    public void setID(Integer id){
        this.pollID = id; 
    }

    public List<PollOption> getoptions(){
        return options; 
    }

    public void setOptions(List<PollOption> options){
        this.options = options;
        for(int i = 0; i < options.size(); i++){
            votes.put(options.get(i), 0); 

        } 
    }

    public List<String> getOptionsNames(){
        List<String> names = new ArrayList<>(); 
        for (int i = 0; i < options.size(); i++){
            names.add(options.get(i).getOptionName()); 

        }
        return names; 
}

    public void Vote(PollOption option){
        if(votes.containsKey(option)    ){
            votes.put(option, votes.get(option) + 1);
        } 
    }
    public PollOption getWinner(){
        PollOption winner = null;
        int maxVotes = 0;

        for (PollOption option : votes.keySet()) {
            int voteCount = votes.get(option);
            if (voteCount > maxVotes) {
                maxVotes = voteCount;
                winner = option;
            }
        }
        if(!(winner == null)){
            return winner; 

        }
        else {
            throw new IllegalStateException("No votes have been cast");}
        }

    
}
