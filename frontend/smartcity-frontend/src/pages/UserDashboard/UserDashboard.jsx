import { useEffect, useState } from "react";
import styles from "./UserDashboard.module.css";
import api from "../../utils/axiosClient";
import { useNavigate } from "react-router-dom";
import { getUser } from "../../utils/auth";

const UserDashboard = () => {
  const navigate = useNavigate();
  const user = getUser(); // SAFE USER FETCH

  // If user is missing, redirect to login
  useEffect(() => {
    if (!user) {
      navigate("/login");
    }
  }, []);

  // Prevent component crash if user = null
  if (!user) {
    return <p className={styles.loading}>Redirecting...</p>;
  }

  const [myStats, setMyStats] = useState({
    total: 0,
    pending: 0,
    inProgress: 0,
    resolved: 0,
  });

  const [recent, setRecent] = useState([]);

  const fetchMyIssues = async () => {
    try {
      const res = await api.get(`/issues/user/${user.id}`);
      const issues = res.data || [];

      setMyStats({
        total: issues.length,
        pending: issues.filter((i) => i.status === "PENDING").length,
        inProgress: issues.filter((i) => i.status === "IN_PROGRESS").length,
        resolved: issues.filter((i) => i.status === "RESOLVED").length,
      });

      setRecent(issues.slice(0, 5));
    } catch (err) {
      console.error("Failed to load issues", err);
    }
  };

  useEffect(() => {
    fetchMyIssues();
  }, []);

  return (
    <div className={styles.container}>
      <h2>Welcome, {user.name || user.email}</h2>

      <div className={styles.statsGrid}>
        <div className={styles.card}>
          <h3>Total Issues</h3>
          <p>{myStats.total}</p>
        </div>

        <div className={styles.card}>
          <h3>Pending</h3>
          <p>{myStats.pending}</p>
        </div>

        <div className={styles.card}>
          <h3>In Progress</h3>
          <p>{myStats.inProgress}</p>
        </div>

        <div className={styles.card}>
          <h3>Resolved</h3>
          <p>{myStats.resolved}</p>
        </div>
      </div>

      <div className={styles.quickActions}>
        <button onClick={() => navigate("/user/report")}>
          Report New Issue
        </button>
        <button onClick={() => navigate("/user/my-issues")}>
          My Issues
        </button>
      </div>

      <h3 className={styles.recentTitle}>Recent Issues</h3>

      <div className={styles.recentList}>
        {recent.length === 0 && <p>No issues reported yet.</p>}

        {recent.map((issue) => (
          <div key={issue.id} className={styles.issueItem}>
            <p><strong>{issue.title}</strong></p>
            <p>Status: {issue.status}</p>
            <p className={styles.small}>
              {issue.reportedDate?.substring(0, 10)}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default UserDashboard;
