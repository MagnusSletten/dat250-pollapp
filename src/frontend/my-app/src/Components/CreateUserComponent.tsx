import { use, useState } from "react";

export default function CreateUserComponent (){
    const [userName, setUsername] = useState("");
    const [password, setPassword] = useState(""); 
    const [email, setEmail] = useState("");
    const url = "http://localhost:8080/users"
    const [message,setMessage] = useState("");

    const sendUser = async () =>{ 
      const res = await fetch(url, {
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
        setMessage(await res.text())
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
    
