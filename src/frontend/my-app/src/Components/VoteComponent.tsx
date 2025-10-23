import { useState } from "react"
import { Vote, VoteOption, VoteOptions } from "./Model/Poll";
import { BACKEND_URL } from './Constants/constants.js';

export default function VotePoll({userName}: {userName:string}){
    const [pollID, setPollID] = useState(1);
    const url = BACKEND_URL + "/polls/"+pollID+"/votes"
    const [error, setError] = useState<string | null>(null);
    const [pollJson, setPollJson] = useState(null);
    const [pollOptions, setPollOptions] = useState<VoteOptions>();
    const [votes,setVotes] = useState([])
    const [pollTitle, setPolltitle] = useState("")
    const [pollQuestion, setPollQuestion] = useState("")
   

    const getVotes = async () => {
        const res = await fetch(url+"/results");
        const data = await res.json(); 
        setVotes(data);
        }
    const vote = async(presentationOrder:number) =>{
        try{
        const vote:Vote = new Vote(presentationOrder,pollID, null)
        if(userName){
            vote.userName = userName; 
        }
        const getCookieRaw = (name: string) =>
        document.cookie.split('; ')
            .find(c => c.startsWith(name + '='))?.split('=')[1] ?? '';
        const xsrf = getCookieRaw('XSRF-TOKEN');
        console.log(vote)
        const response = await fetch(url, {
        method: 'POST',
        credentials: "include",
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': xsrf,
        },
        body: JSON.stringify(vote.toJSON())});
        getVotes();
        }
        catch(e: any){
        setError("Error voting");
       }
    }
 
    const  getPoll = async() =>{
       setError("")
       try {
       const res = await fetch(BACKEND_URL+"/polls/"+pollID);
       const data = await res.json();
       const voteoptions = new VoteOptions().fromJSON(data.options);
       setPollOptions(voteoptions)
       console.log((data))
       console.log(voteoptions)
       setPollJson(data);
       setPolltitle(data.title)
       setPollID(data.id)
       getVotes();
       setPollQuestion(data.question);
       }
       catch(e: any){
        setError("Error fetching poll with id"+pollID);
       }
    }

    const createOptionButtons = () => {
        return(
            <div className="voteOptionsBox">
        {pollOptions?.getVoteOptions().map(option => (
            <div className="voteOption"
            key={option.optionId}>
            <span className="caption">{option.getCaption()}</span>
            <button
            onClick={() => vote(option.presentationOrder)}
            >vote</button>
            <span className="voteCount">
            {votes[option.presentationOrder] ?? 0}</span>
            </div>
            
        ))}
          </div>
    )

    }
    

    return (
        <div>
            <input
            placeholder={"Set Poll ID"}
            onChange={(e)=> setPollID(Number(e.target.value))}>
            </input>
            <button
            onClick={() => getPoll()}
            >
            Get Poll
            </button>
            <h3>{error}</h3>
            {pollJson && (
            <>
            <h2>{pollTitle}</h2>
            <h3>Question: {pollQuestion}</h3>
            {createOptionButtons()}
            </>
            )}
                  
            
        </div>
        
    )

}