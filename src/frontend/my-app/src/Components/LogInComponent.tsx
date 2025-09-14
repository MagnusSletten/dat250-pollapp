type LogInProps = {
  userName: string;
  setUsername: React.Dispatch<React.SetStateAction<string>>;
  setLoginStatus: React.Dispatch<React.SetStateAction<boolean>>;
};

export default function LogInComponent({userName, setUsername, setLoginStatus}: LogInProps){

    return (
        <div className="login-section"> 
            <input
            className="loginField"
            onChange={(e) => setUsername(e.target.value)}
            value={userName}
            placeholder={"Username"}
            >
            </input>
            <button
            className="loginButton"
            onClick={e => setLoginStatus(true)}
            >Log in</button>
        </div>
    )
    
   
}