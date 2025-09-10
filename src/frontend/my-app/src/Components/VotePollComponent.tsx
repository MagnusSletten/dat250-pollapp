import { useState } from "react"
import { Vote } from "./Poll";

export default function VotePoll(){
    const [pollID, setPollID] = useState("1");
    const url = 'http://localhost:8080/polls/'
    const voteUrl = url+pollID+"/votes"
    var [pollJson, setPollJson] = useState(null)
    const [pollOptions, setPollOptions] = useState([]);
   
    const vote = async(optionId) =>{
        const vote:Vote = new Vote(optionId)

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
       const pollJsonReturn = await res.json();
       setPollJson(pollJsonReturn);
       setPollOptions(pollJsonReturn.voteOptions)
        }

    
    const createOptionButtons = () => {
        return(
        pollOptions.map(option => (
            <button
            onClick={() => vote()}
            >{option.caption} </button>
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