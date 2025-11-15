import { useEffect, useState } from "react";
import DashboardLayout from "../../components/Layout/DashboardLayout";
import api from "../../utils/axiosClient";
import styles from "./SlaBreaches.module.css";

const SlaBreaches = () => {
  const [issues, setIssues] = useState([]);

  const loadData = async () => {
    try {
      const res = await api.get("/admin/sla-breaches?slaHours=48");
      setIssues(res.data);
    } catch (err) {
      console.error("Failed to load SLA breaches", err);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  return (
    <DashboardLayout>
      <div className={styles.container}>
        <h2>SLA Breached Issues (Over 48 Hours)</h2>

        {issues.length === 0 && <p>No SLA breaches found.</p>}

        {issues.map((i) => (
          <div key={i.issueId} className={styles.card}>
            <h3>{i.title}</h3>
            <p><strong>Department:</strong> {i.departmentName}</p>
            <p><strong>Status:</strong> {i.status}</p>
            <p><strong>Reported:</strong> {i.reportedDate.substring(0, 10)}</p>
            <p><strong>Hours Open:</strong> {i.hoursOpen}</p>
          </div>
        ))}
      </div>
    </DashboardLayout>
  );
};

export default SlaBreaches;
