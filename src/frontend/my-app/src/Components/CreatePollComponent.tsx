import { useState,  } from 'react';
import { Poll, VoteOptions } from "./Model/Poll"; 
import PollView from './PollView'

function CustomPoll():Poll  {
  
  const question = ""
  const poll = new Poll(question);
  
  
  return poll;
}

function CreatePoll({  userName,
  loginStatus,
}: {
  userName: string;
  loginStatus: boolean;
   })  {
  
  const poll = CustomPoll();
  const [response, setResponse] = useState(""); 
  const setCreator = ()=> {
    poll.setCreator(userName)
  } 

  const sendPoll = async () => {
    const url = 'http://localhost:8080/polls'
    
    try {
      setCreator()
      const res = await fetch(url, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(poll.toJSON())}); 
      setResponse(await res.text()); 
      }

      catch(error) {
        setResponse(error.message);
    }
  }
  if(loginStatus){
  return (

    <>
      <PollView poll={poll} />
      <button
      onClick={sendPoll}>
      Send Poll</button>
      <span className='backend-response'>{response}</span> 
    </>
   

  )
  }

}

export default CreatePoll
