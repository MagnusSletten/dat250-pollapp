import { useState } from "react";
import CreatePollComponent from './Components/CreatePollComponent'
import VotePollComponent from "./Components/VoteComponent";
import LogInComponent from "./Components/LogInComponent";
import CreateUserComponent from "./Components/CreateUserComponent";

function App() {
  const [pageState, setPageState] = useState(0);
  const [loginStatus, setLoginStatus] = useState(false);
  const [userName, setUsername] = useState("");

  return (

    <>
      <div className="login-box">
        <button className="page-state-button" onClick={() => setPageState(0)}>Login</button>
      </div>
      <div className="page-tabs">
        <button className="page-state-button" onClick={() => setPageState(1)}>Poll-Creation</button>
        <button className="page-state-button" onClick={() => setPageState(2)}>Vote</button>
        <button className="page-state-button" onClick={() => setPageState(3)}>Create User</button>
      </div>
      {pageState === 1 ? (
        <CreatePollComponent
          userName={userName}
          loginStatus={loginStatus}
        />
      ) : pageState === 2 ? (
        <VotePollComponent 
         userName= {userName}/>
      ) : pageState === 0 ? (
        <LogInComponent
          userName={userName}
          setLoginStatus={setLoginStatus}
          setUsername={setUsername}
        />)
        : (
          <CreateUserComponent/>
        )
      }
      {loginStatus && (
        <h2>Logged in as {userName}</h2>
      )}
    </>
  );
}

export default App;

