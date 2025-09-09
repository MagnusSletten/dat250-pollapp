import { Children, use, useState } from "react";
import CreatePollComponent from './Components/CreatePollComponent'
import { Poll } from "./Components/Poll";
import VotePollComponent from "./Components/VotePollComponent";


function CustomPoll():Poll  {
  
  const question = ""
  const poll = new Poll(question);
  
  return poll;
}


function App() {
  const [pageState, setPageState] = useState(0);
  const Components = [CreatePollComponent,VotePollComponent]
  const ActiveComponent = Components[pageState];

  return (
    <>
    <div className="page-tabs"> 
      <button className="page-state-button"
      onClick={()=> setPageState(0)}
      >Poll-Creation</button>
      <button className="page-state-button"
      onClick={()=> setPageState(1)}
      >Vote</button>
    </div>
    <ActiveComponent/>
   </>
  )

}

export default App

