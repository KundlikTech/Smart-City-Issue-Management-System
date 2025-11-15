import { useState } from "react";
import DashboardLayout from "../../components/Layout/DashboardLayout";
import styles from "./UserReportIssue.module.css";
import { getUser } from "../../utils/auth";
import api from "../../utils/axiosClient";

const ReportIssue = () => {
  const user = getUser();

  if (!user) {
    return <DashboardLayout><p>User not logged in!</p></DashboardLayout>;
  }

  const [form, setForm] = useState({
    title: "",
    description: "",
    category: "",
    location: ""
  });

  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSuccess("");
    setError("");

    try {
      const res = await api.post(`/issues?userId=${user.id}`, form);
      setSuccess("Issue reported successfully!");
      setForm({ title: "", description: "", category: "", location: "" });

    } catch (err) {
      console.error(err);
      setError("Failed to report issue.");
    }
  };

  return (
    <DashboardLayout>
      <div className={styles.container}>
        <h2>Report a New Issue</h2>

        {success && <p className={styles.success}>{success}</p>}
        {error && <p className={styles.error}>{error}</p>}

        <form onSubmit={handleSubmit} className={styles.form}>

          <label>Title</label>
          <input name="title" value={form.title} onChange={handleChange} required />

          <label>Description</label>
          <textarea name="description" value={form.description} onChange={handleChange} required />

          <label>Category</label>
          <select name="category" value={form.category} onChange={handleChange} required>
            <option value="">Select Category</option>
            <option value="ROADS">Roads</option>
            <option value="ELECTRICITY">Electricity</option>
            <option value="WATER">Water</option>
            <option value="WASTE">Waste Management</option>
          </select>

          <label>Location</label>
          <input name="location" value={form.location} onChange={handleChange} required />

          <button type="submit">Submit Issue</button>
        </form>
      </div>
    </DashboardLayout>
  );
};

export default ReportIssue;
