import { use, useState } from 'react'
import './App.css'
import { Poll, Question } from "./Components/Poll"; 
import PollView from './Components/PollView'

function CustomPoll():Poll  {
  const poll = new Poll();
  const question = new Question();
  poll.setName("This is a bad poll")
  return poll;
}

function App() {
  const poll = CustomPoll(); 
  
  


  return (
    <PollView poll={poll} />
  )

}

export default App
