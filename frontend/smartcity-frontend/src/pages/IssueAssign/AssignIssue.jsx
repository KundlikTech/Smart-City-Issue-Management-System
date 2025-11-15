import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import DashboardLayout from "../../components/Layout/DashboardLayout";
import api from "../../utils/axiosClient";
import styles from "./AssignIssue.module.css";

const AssignIssue = () => {
  const { issueId } = useParams();
  const navigate = useNavigate();

  const [departments, setDepartments] = useState([]);

  useEffect(() => {
    loadDepartments();
  }, []);

  const loadDepartments = async () => {
    try {
      const res = await api.get("/departments");
      setDepartments(res.data);
    } catch (err) {
      console.error("Failed to load departments", err);
    }
  };

  const assignDept = async (deptId) => {
    try {
      await api.post(`/admin/issues/${issueId}/assign/${deptId}`);
      navigate("/admin/issues");
    } catch (err) {
      console.error("Failed to assign issue");
    }
  };

  return (
    <DashboardLayout>
      <div className={styles.container}>
        <h2>Assign Issue #{issueId}</h2>

        <ul className={styles.list}>
          {departments.map((d) => (
            <li key={d.id}>
              {d.name}
              <button onClick={() => assignDept(d.id)}>Assign</button>
            </li>
          ))}
        </ul>
      </div>
    </DashboardLayout>
  );
};

export default AssignIssue;
