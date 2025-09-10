import { useState } from "react"
import { Vote, VoteOption, VoteOptions } from "./Poll";

export default function VotePoll(){
    const [pollID, setPollID] = useState("1");
    const url = 'http://localhost:8080/polls/'
    const voteUrl = url+pollID+"/votes"
    var [pollJson, setPollJson] = useState(null);
    const [pollOptions, setPollOptions] = useState<VoteOptions>();
   
    const vote = async(optionId) =>{
        const vote:Vote = new Vote(optionId,pollID)

        const response = await fetch(voteUrl, {
        method: 'POST',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(vote.toJSON())}); 
        }

    const  getPoll = async() =>{
       const res = await fetch(url+pollID);
       const data = await res.json();
       const voteoptions = new VoteOptions().fromJSON(data.voteOptions);
       setPollOptions(voteoptions)
       setPollJson(data);
        }

    
    const createOptionButtons = () => {
        return(
        pollOptions?.getVoteOptions().map(option => (
            <button
            onClick={() => vote(option.optionId)}
            >{option.getCaption()}</button>
        ))
    )

    }
    

    return (
        <div>
            <input
            placeholder={"Set Poll ID"}
            onChange={(e)=> setPollID(e.target.value)}>
            </input>
            <button
            onClick={() => getPoll()}
            >
            Get Poll
            </button>
            {pollJson && (
            <>{createOptionButtons()}</>
            )}
            
        </div>
        
    )

}