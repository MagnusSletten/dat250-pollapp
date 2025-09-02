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

  const sendPoll = () => {
  fetch('http://localhost:8080/polls/', {
  method: 'POST',
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
  },
  body: JSON.stringify(poll.toJSON()),
});


  } 
  
  


  return (
    <>
    <PollView poll={poll} />
    <button

    onClick={sendPoll}>
    Send Poll</button>
    </>

  )

}

export default App
