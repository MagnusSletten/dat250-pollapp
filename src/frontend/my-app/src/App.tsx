import { useState } from "react";
import CreatePollComponent from './Components/CreatePollComponent'
import VotePollComponent from "./Components/VoteComponent";
import LogInComponent from "./Components/LogInComponent";
import CreateUserComponent from "./Components/CreateUserComponent";

export default function App() {
    const [pageState, setPageState] = useState(0);
    const [loginStatus, setLoginStatus] = useState(false);
    const [userName, setUsername] = useState("");

    return (
        <>
            <div className="top-utility-bar">
                <div className="auth-actions">
                    <button
                        className="btn-utility"
                        onClick={() => setPageState(0)}
                    >
                        { loginStatus ? "User profile" : "Log in"}
                    </button>

                    <button
                        className="btn-utility"
                        onClick={() => setPageState(3)}
                    >
                        Create User
                    </button>
                </div>
            </div>

            <div className="Title">
                <h1>Welcome to our polling app</h1>
            </div>
            <div className="app-container">

                <div className="page-tabs">
                    <button className="page-state-button" onClick={() => setPageState(1)}>Poll-Creation</button>
                    <button className="page-state-button" onClick={() => setPageState(2)}>Vote</button>
                </div>

                <div className="content">
                    {pageState === 1 ? (
                        <CreatePollComponent userName={userName} loginStatus={loginStatus} />
                    ) : pageState === 2 ? (
                        <VotePollComponent userName={userName} />
                    ) : pageState === 0 ? (
                        <LogInComponent
                            userName={userName}
                            setLoginStatus={setLoginStatus}
                            setUsername={setUsername}
                            loginStatus={loginStatus}
                        />
                    ) : (
                        <CreateUserComponent />
                    )}
                </div>
                {loginStatus && <h2 className="login-status">Logged in as {userName}</h2>}
            </div>
        </>
    );
}