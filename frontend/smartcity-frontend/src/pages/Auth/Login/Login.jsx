import { useState } from "react";
import styles from "./Login.module.css";
import api from "../../../utils/axiosClient";
import { saveAuth } from "../../../utils/auth";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();
    setErrorMsg("");

    try {
      const res = await api.post("/users/login", { email, password });

      const { token, id, name, role } = res.data;

      // Save authentication info
      saveAuth(token, { id, email, name, role });

      // Redirect based on role
      if (role === "ADMIN") {
        navigate("/admin/dashboard");
      } else {
        navigate("/dashboard"); // ✔ Correct route
      }
    } catch (err) {
      console.error("Login failed:", err);
      setErrorMsg("Invalid email or password");
    }
  };

  return (
    <div className={styles.loginContainer}>
      <form className={styles.loginBox} onSubmit={handleLogin}>
        <h2>Login</h2>

        {errorMsg && <p className={styles.error}>{errorMsg}</p>}

        <input
          type="email"
          placeholder="Enter email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />

        <input
          type="password"
          placeholder="Enter password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />

        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default Login;
