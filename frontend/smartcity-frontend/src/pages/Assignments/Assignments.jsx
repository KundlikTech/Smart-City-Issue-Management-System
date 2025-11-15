import { useEffect, useState } from "react";
import DashboardLayout from "../../components/Layout/DashboardLayout";
import api from "../../utils/axiosClient";
import styles from "./Assignments.module.css";

const Assignments = () => {
  const [assignments, setAssignments] = useState([]);

  useEffect(() => {
    load();
  }, []);

  const load = async () => {
    try {
      const res = await api.get("/admin/assignments/active");
      setAssignments(res.data);
    } catch (err) {
      console.log("Failed to load assignments");
    }
  };

  return (
    <DashboardLayout>
      <div className={styles.container}>
        <h2>Active Assignments</h2>

        {assignments.length === 0 && <p>No active work.</p>}

        {assignments.map((a) => (
          <div key={a.id} className={styles.card}>
            <p><strong>Issue:</strong> {a.issue?.title}</p>
            <p><strong>Department:</strong> {a.department?.name}</p>
            <p><strong>Status:</strong> {a.status}</p>
          </div>
        ))}
      </div>
    </DashboardLayout>
  );
};

export default Assignments;
