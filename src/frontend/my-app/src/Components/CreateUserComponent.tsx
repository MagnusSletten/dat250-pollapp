import { use, useState } from "react";
import { BACKEND_URL } from './Constants/constants.js';

export default function CreateUserComponent (){
    const [userName, setUsername] = useState("");
    const [password, setPassword] = useState(""); 
    const [email, setEmail] = useState("");
    const [message,setMessage] = useState("");

    const sendUser = async (e) =>{ 
      e.preventDefault(); 
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
         <form onSubmit={sendUser}>
      <input
        onChange={(e) => setUsername(e.target.value)}
        placeholder="Set username"
        required
      />
      <input
        onChange={(e) => setEmail(e.target.value)}
        placeholder="Set email"
        required
      />
      <input
        type="password"
        onChange={(e) => setPassword(e.target.value)}
        placeholder="Set password"
        required
      />
      <button type="submit">Send User</button>
    </form>
        <h3>{message}</h3>
        </div>


    )

}
    
