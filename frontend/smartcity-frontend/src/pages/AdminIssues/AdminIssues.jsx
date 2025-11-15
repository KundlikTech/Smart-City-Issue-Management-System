import { useEffect, useState } from "react";
import DashboardLayout from "../../components/Layout/DashboardLayout";
import api from "../../utils/axiosClient";
import { useNavigate } from "react-router-dom";
import styles from "./AdminIssues.module.css";

const AdminIssues = () => {
  const [issues, setIssues] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    loadIssues();
  }, []);

  const loadIssues = async () => {
    try {
      const res = await api.get("/issues");
      setIssues(res.data);
    } catch (err) {
      console.error("Failed to load issues", err);
    }
  };

  return (
    <DashboardLayout>
      <div className={styles.container}>
        <h2>All Issues</h2>

        <table className={styles.table}>
          <thead>
            <tr>
              <th>ID</th>
              <th>Title</th>
              <th>Status</th>
              <th>User</th>
              <th>Assign</th>
            </tr>
          </thead>

          <tbody>
            {issues.map((i) => (
              <tr key={i.id}>
                <td>{i.id}</td>
                <td>{i.title}</td>
                <td>{i.status}</td>
                <td>{i.user?.email}</td>
                <td>
                  <button
                    onClick={() => navigate(`/admin/issues/assign/${i.id}`)}
                  >
                    Assign
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </DashboardLayout>
  );
};

export default AdminIssues;
