import { useEffect, useState } from "react";
import DashboardLayout from "../../components/Layout/DashboardLayout";
import { useParams } from "react-router-dom";
import api from "../../utils/axiosClient";
import styles from "./IssueDetails.module.css";

const IssueDetails = () => {
  const { id } = useParams();
  const [issue, setIssue] = useState(null);

  const loadIssue = async () => {
    try {
      const res = await api.get(`/issues/${id}`);
      setIssue(res.data);
    } catch (e) {
      console.error("Failed to load issue", e);
    }
  };

  useEffect(() => {
    loadIssue();
  }, []);

  if (!issue) return <DashboardLayout>Loading...</DashboardLayout>;

  return (
    <DashboardLayout>
      <div className={styles.container}>
        <h2>{issue.title}</h2>
        <p><strong>Status:</strong> {issue.status}</p>
        <p><strong>Category:</strong> {issue.category}</p>
        <p><strong>Description:</strong> {issue.description}</p>
        <p><strong>Location:</strong> {issue.location}</p>

        <p className={styles.small}>
          Reported on: {issue.reportedDate?.substring(0, 10)}
        </p>
      </div>
    </DashboardLayout>
  );
};

export default IssueDetails;
