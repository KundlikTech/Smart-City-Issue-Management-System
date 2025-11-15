import React, { useEffect, useState } from "react";
import styles from "./IssuesList.module.css";
import api from "../../utils/axiosClient";
import { useNavigate } from "react-router-dom";

const IssueList = () => {
  const navigate = useNavigate();
  const [issues, setIssues] = useState([]);

  useEffect(() => {
    api.get("/issues")
      .then((res) => setIssues(res.data))
      .catch(console.error);
  }, []);

  return (
    <div className={styles.container}>
      <h1 className={styles.heading}>All Issues</h1>

      <table className={styles.table}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Category</th>
            <th>Status</th>
            <th>Reported Date</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {issues.map((issue) => (
            <tr key={issue.id}>
              <td>{issue.id}</td>
              <td>{issue.title}</td>
              <td>{issue.category}</td>

              <td>
                <span
                  className={`${styles.badge} ${
                    issue.status === "PENDING"
                      ? styles.pending
                      : issue.status === "IN_PROGRESS"
                      ? styles.progress
                      : styles.resolved
                  }`}
                >
                  {issue.status}
                </span>
              </td>

              <td>{issue.reportedDate?.split("T")[0]}</td>

              <td>
                <button
                  className={styles.manageBtn}
                  onClick={() =>
                    navigate(`/admin/issues/assign/${issue.id}`)
                  }
                >
                  Manage Issue
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default IssueList;
