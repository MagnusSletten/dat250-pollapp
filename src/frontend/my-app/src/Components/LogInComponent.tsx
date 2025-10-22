import { useEffect, useState } from "react";

type LogInProps = {
  userName: string;
  setUsername: React.Dispatch<React.SetStateAction<string>>;
  setLoginStatus: React.Dispatch<React.SetStateAction<boolean>>;
};

const url = 'http://localhost:8080';

export default function LogInComponent({
  userName,
  setUsername,
  setLoginStatus,
}: LogInProps) {
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState<string | null>(null);

  const login = async () => {
    setErr(null);
    setLoading(true);
    try {
      const body = new URLSearchParams({
        username: userName,
        password: password,
      });
      
      const res = await fetch(url+"/users/auth/login", {
        method: "POST",
        credentials: "include", // send session cookie (JSESSIONID)
        headers: {
          "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
        },
        body: body.toString(),
      });

      if (!res.ok) throw new Error(`Login failed (${res.status})`);
      await fetch(url+'/auth/csrf', { credentials: 'include' });
      setLoginStatus(true);
    } catch (e: any) {
      setErr(e.message ?? "Login failed");
      setLoginStatus(false);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-section">
      <input
        className="loginField"
        onChange={(e) => setUsername(e.target.value)}
        value={userName}
        placeholder="Username"
        autoComplete="username"
      />
      <input
        className="loginField"
        type="password"
        onChange={(e) => setPassword(e.target.value)}
        value={password}
        placeholder="Password"
        autoComplete="current-password"
      />
      <button className="loginButton" onClick={login} disabled={loading}>
        {loading ? "Logging in..." : "Log in"}
      </button>
      {err && <div className="error">{err}</div>}
    </div>
  );
}