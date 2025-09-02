import { useState, useSyncExternalStore } from 'react';
import './App.css'
import { Poll, Question } from "./Components/Poll"; 
import PollView from './Components/PollView'

function CustomPoll():Poll  {
  const poll = new Poll();
  const question = new Question();
  
  return poll;
}



function App() {
  const poll = CustomPoll();
  const [response, setResponse] = useState(""); 

  const sendPoll = async () => {
  const url = 'http://localhost:8080/polls/'
  
  try {
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

export default App
