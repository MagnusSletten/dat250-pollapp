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

  const getCookieRaw = (name: string) =>
  document.cookie.split('; ')
    .find(c => c.startsWith(name + '='))?.split('=')[1] ?? '';

  const sendPoll = async () => {
    const url = 'http://localhost:8080';

    
    try {
      setCreator()
      const raw = document.cookie.split('; ')
      .find(c => c.startsWith('XSRF-TOKEN='))?.split('=')[1];
    const xsrf = getCookieRaw('XSRF-TOKEN');
    console.log(xsrf)
    if (!xsrf) throw new Error('No XSRF-TOKEN cookie present');
      
    const res = await fetch(url+'/polls', {
      method: 'POST',
      credentials: 'include', // sends cookies automatically
      headers: {
        'Content-Type': 'application/json',
         'X-XSRF-TOKEN': xsrf,
        
      },
      body: JSON.stringify(pollRef.current.toJSON()),
    });
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
