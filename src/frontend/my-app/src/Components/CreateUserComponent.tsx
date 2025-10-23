import { use, useState } from "react";
import { BACKEND_URL } from './Constants/constants.js';

export default function CreateUserComponent (){
    const [userName, setUsername] = useState("");
    const [password, setPassword] = useState(""); 
    const [email, setEmail] = useState("");
    const [message,setMessage] = useState("");

    const sendUser = async () =>{ 
      const res = await fetch(BACKEND_URL+"/users", {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(
        {
            "username": userName,
            "email": email,
            "password": password
        }
      )});
      if(res.ok){
        setMessage("User sent successfully")
      }
    }

    return(
        <div className="create-user-box">
        <input
        onChange={(e) => setUsername(e.target.value)}
        placeholder="Set username"
        ></input>
        <input
        onChange={(e) => setEmail(e.target.value)}
        placeholder="Set email"

        ></input>
        <input
        onChange={(e) => setPassword(e.target.value)}
        placeholder="Set password"

        ></input>
        <button
        onClick={sendUser}
        >Send User</button>
        <h3>{message}</h3>
        </div>


    )

}
    
