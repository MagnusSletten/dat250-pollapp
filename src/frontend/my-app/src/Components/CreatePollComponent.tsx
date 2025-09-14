import { useState,  } from 'react';
import { Poll } from "./Model/Poll"; 
import PollView from './PollView'



function CreatePoll({  userName,
  loginStatus,
}: {
  userName: string;
  loginStatus: boolean;
   })  {
  
  const [poll, setPoll] = useState(new Poll());
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
      catch(error:unknown){
        if(error instanceof Error){
          setResponse(error.message);
      } else { setResponse(String(error));
        
      }
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
