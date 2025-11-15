import { useEffect, useState } from "react";
import DashboardLayout from "../../components/Layout/DashboardLayout";
import api from "../../utils/axiosClient";
import styles from "./AdminDashboard.module.css";

const AdminDashboard = () => {
  const [stats, setStats] = useState({
    totalIssues: 0,
    pending: 0,
    inProgress: 0,
    resolved: 0
  });

  const [trend7, setTrend7] = useState([]);
  const [trend6, setTrend6] = useState([]);

  useEffect(() => {
    loadStats();
  }, []);

  const loadStats = async () => {
    try {
      const res = await api.get("/admin/stats");
      setStats(res.data);

      const t1 = await api.get("/admin/last7days");
      setTrend7(t1.data);

      const t2 = await api.get("/admin/last6months");
      setTrend6(t2.data);

    } catch (err) {
      console.error("Failed to load admin dashboard", err);
    }
  };

  return (
    <DashboardLayout>
      <div className={styles.container}>
        <h2>Admin Dashboard</h2>

        <div className={styles.grid}>
          <div className={styles.card}>
            <h3>Total Issues</h3>
            <p>{stats.totalIssues}</p>
          </div>

          <div className={styles.card}>
            <h3>Pending</h3>
            <p>{stats.pending}</p>
          </div>

          <div className={styles.card}>
            <h3>In Progress</h3>
            <p>{stats.inProgress}</p>
          </div>

          <div className={styles.card}>
            <h3>Resolved</h3>
            <p>{stats.resolved}</p>
          </div>
        </div>

        <h3>Last 7 Days Trend</h3>
        <ul>
          {trend7.map((t, idx) => (
            <li key={idx}>{t.date} → {t.count}</li>
          ))}
        </ul>

        <h3>Last 6 Months Trend</h3>
        <ul>
          {trend6.map((t, idx) => (
            <li key={idx}>{t.month} → {t.count}</li>
          ))}
        </ul>
      </div>
    </DashboardLayout>
  );
};

export default AdminDashboard;
