import { useEffect, useState } from "react";
import DashboardLayout from "../../components/Layout/DashboardLayout";
import api from "../../utils/axiosClient";
import styles from "./AverageTimes.module.css";

const AverageTimes = () => {
  const [avg, setAvg] = useState({
    avgResponseHours: 0,
    avgResolutionHours: 0
  });

  const loadData = async () => {
    try {
      const res = await api.get("/admin/avg-times");
      setAvg(res.data);
    } catch (err) {
      console.error("Failed to load average times", err);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  return (
    <DashboardLayout>
      <div className={styles.container}>
        <h2>Average Handling Times</h2>

        <div className={styles.card}>
          <h3>Average Response Time</h3>
          <p>{avg.avgResponseHours?.toFixed(2)} hours</p>
        </div>

        <div className={styles.card}>
          <h3>Average Resolution Time</h3>
          <p>{avg.avgResolutionHours?.toFixed(2)} hours</p>
        </div>
      </div>
    </DashboardLayout>
  );
};

export default AverageTimes;
