import { useState } from "react";
import CreatePollComponent from './Components/CreatePollComponent'
import VotePollComponent from "./Components/VoteComponent";
import LogInComponent from "./Components/LogInComponent";

function App() {
  const [pageState, setPageState] = useState(0);
  const [loginStatus, setLoginStatus] = useState(false);
  const [userName, setUsername] = useState("");

  return (
    <>
      <div className="page-tabs">
        <button className="page-state-button" onClick={() => setPageState(0)}>Poll-Creation</button>
        <button className="page-state-button" onClick={() => setPageState(1)}>Vote</button>
        <button className="page-state-button" onClick={() => setPageState(2)}>Login</button>
      </div>
      {pageState === 0 ? (
        <CreatePollComponent
          userName={userName}
          loginStatus={loginStatus}
        />
      ) : pageState === 1 ? (
        <VotePollComponent 
         userName= {userName}/>
      ) : (
        <LogInComponent
          userName={userName}
          setLoginStatus={setLoginStatus}
          setUsername={setUsername}
        />
      )}
      {loginStatus && (
        <h2>Logged in as {userName}</h2>
      )}
    </>
  );
}

export default App;

