import { useEffect, useState } from "react";
import DashboardLayout from "../../components/Layout/DashboardLayout";
import api from "../../utils/axiosClient";
import styles from "./TopDepartments.module.css";

const TopDepartments = () => {
  const [list, setList] = useState([]);

  const loadData = async () => {
    try {
      const res = await api.get("/admin/top-departments?days=30&limit=10");
      setList(res.data);
    } catch (err) {
      console.error("Failed to load top departments", err);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  return (
    <DashboardLayout>
      <div className={styles.container}>
        <h2>Top Performing Departments (Last 30 Days)</h2>

        <table className={styles.table}>
          <thead>
            <tr>
              <th>Rank</th>
              <th>Department</th>
              <th>Issues Resolved</th>
            </tr>
          </thead>

          <tbody>
            {list.map((d, idx) => (
              <tr key={d.departmentId}>
                <td>{idx + 1}</td>
                <td>{d.departmentName}</td>
                <td>{d.count}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </DashboardLayout>
  );
};

export default TopDepartments;
