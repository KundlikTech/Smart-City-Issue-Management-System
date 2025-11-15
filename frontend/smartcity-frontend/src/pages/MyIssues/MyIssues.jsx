import { useEffect, useState } from "react";
import DashboardLayout from "../../components/Layout/DashboardLayout";
import api from "../../utils/axiosClient";
import { getUser } from "../../utils/auth";
import styles from "./MyIssues.module.css";

const MyIssues = () => {
  const user = getUser();
  const [issues, setIssues] = useState([]);

  const loadIssues = async () => {
    try {
      const res = await api.get(`/issues/user/${user.id}`);
      setIssues(res.data);
    } catch (err) {
      console.log("Failed to load my issues", err);
    }
  };

  useEffect(() => {
    loadIssues();
  }, []);

  return (
    <DashboardLayout>
      <div className={styles.container}>
        <h2>My Reported Issues</h2>

        {issues.length === 0 && <p>No issues reported yet.</p>}

        {issues.map((i) => (
          <div key={i.id} className={styles.card}>
            <h3>{i.title}</h3>
            <p>{i.description}</p>
            <p><strong>Status:</strong> {i.status}</p>
            <p className={styles.date}>
              Reported: {i.reportedDate?.substring(0, 10)}
            </p>
          </div>
        ))}
      </div>
    </DashboardLayout>
  );
};

export default MyIssues;
