import { useState } from "react";
import styles from "./Register.module.css";
import axiosClient from "../../../utils/axiosClient";
import { saveAuth } from "../../../utils/auth";
import { useNavigate } from "react-router-dom";

const Register = () => {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
    phone: "",
    address: "",
  });

  const [errorMsg, setErrorMsg] = useState("");

  const onChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setErrorMsg("");

    try {
      const res = await axiosClient.post("/users/register", form);

      // After registration → login user automatically?
      navigate("/login");
    } catch (error) {
      setErrorMsg("Registration failed. Email may already exist.");
    }
  };

  return (
    <div className={styles.registerContainer}>
      <form className={styles.registerBox} onSubmit={handleRegister}>
        <h2>Create Account</h2>

        {errorMsg && <p className={styles.error}>{errorMsg}</p>}

        <input name="name" placeholder="Full Name" value={form.name} onChange={onChange} required />
        <input type="email" name="email" placeholder="Email Address" value={form.email} onChange={onChange} required />
        <input type="password" name="password" placeholder="Password" value={form.password} onChange={onChange} required />

        <input name="phone" placeholder="Phone Number" value={form.phone} onChange={onChange} />
        <input name="address" placeholder="Address" value={form.address} onChange={onChange} />

        <button type="submit">Register</button>

        <p className={styles.loginRedirect}>
          Already have an account? <a href="/login">Login</a>
        </p>
      </form>
    </div>
  );
};

export default Register;
