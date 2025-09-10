export default function LogInComponent({userName, setUsername, setLoginStatus}){

    return (
        <div className="loginSection"> 
            <input
            className="loginField"
            onChange={(e) => setUsername(e.target.value)}
            value={userName}
            defaultValue={"Username"}
            >
            </input>
            <button
            className="loginButton"
            onClick={e => setLoginStatus(true)}
            >Log in</button>
        </div>
    )
    
   
}