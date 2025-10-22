import { useState } from "react"
import { Vote, VoteOption, VoteOptions } from "./Model/Poll";

export default function VotePoll({userName}: {userName:string}){
    const [pollID, setPollID] = useState(1);
    const url = 'http://localhost:8080/polls/'
    const voteUrl = url+pollID+"/votes"
    const [pollJson, setPollJson] = useState(null);
    const [pollOptions, setPollOptions] = useState<VoteOptions>();
    const [votes,setVotes] = useState([])
    const [pollTitle, setPolltitle] = useState("")
    const [pollQuestion, setPollQuestion] = useState("")
   
    const vote = async(presentationOrder:number) =>{
        const vote:Vote = new Vote(presentationOrder,pollID, null)
        if(userName){
            vote.userName = userName; 
        }
        const getCookieRaw = (name: string) =>
        document.cookie.split('; ')
            .find(c => c.startsWith(name + '='))?.split('=')[1] ?? '';
        const xsrf = getCookieRaw('XSRF-TOKEN');
        console.log(vote)
        const response = await fetch(url+pollID+"/votes", {
        method: 'POST',
        credentials: "include",
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': xsrf,
        },
        body: JSON.stringify(vote.toJSON())});
        getVotes()
        }
        const getVotes = async () => {
        const res = await fetch(url+pollID+"/votes/results");
        const data = await res.json(); 
        setVotes(data);
        };

    const  getPoll = async() =>{
       const res = await fetch(url+pollID);
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