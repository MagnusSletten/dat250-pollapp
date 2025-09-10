import { use, useState, useSyncExternalStore } from 'react';
import { Poll, VoteOptions } from "./Poll"; 
import PollView from './PollView'
import LogInComponent from './LogInComponent';

function CustomPoll():Poll  {
  
  const question = ""
  const poll = new Poll(question);
  
  
  return poll;
}

function CreatePoll() {
  const [loginStatus, setLoginStatus] = useState(false);
  const poll = CustomPoll();
  const [response, setResponse] = useState(""); 
  const [userName, setUsername] = useState("")
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
  else return (
    <LogInComponent
    setLoginStatus= {setLoginStatus}
    userName={userName}
    setUsername={setUsername}
    />

  )

}

export default CreatePoll
