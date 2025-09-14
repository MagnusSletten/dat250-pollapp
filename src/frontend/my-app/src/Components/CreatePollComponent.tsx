import { useRef, useState,  } from 'react';
import { Poll } from "./Model/Poll"; 
import PollCreateView from './PollCreateView'



function CreatePoll({  userName,
  loginStatus,
}: {
  userName: string;
  loginStatus: boolean;
   })  {
  

  const [response, setResponse] = useState(""); 
  const pollRef = useRef(new Poll());
  const setCreator = ()=> {
    pollRef.current.setCreator(userName)
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
      body: JSON.stringify(pollRef.current.toJSON())}); 
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
      <PollCreateView poll={pollRef.current} />
      <button
      onClick={sendPoll}>
      Send Poll</button>
      <span className='backend-response'>{response}</span> 
    </>
   

  )
  }

}

export default CreatePoll
