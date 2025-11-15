import { useState, useEffect } from "react";
import styles from "./Notifications.module.css";
import DashboardLayout from "../../components/Layout/DashboardLayout";
import { getNotifications, clearNotifications } from "../../utils/notify";

const Notifications = () => {
  const [list, setList] = useState([]);

  useEffect(() => {
    setList(getNotifications());
  }, []);

  return (
    <DashboardLayout>
      <div className={styles.container}>
        <h2>Notifications</h2>

        <button className={styles.clearBtn} onClick={() => {
          clearNotifications();
          setList([]);
        }}>
          Clear All
        </button>

        {list.length === 0 ? <p>No notifications yet</p> : null}

        {list.map((n, i) => (
          <div key={i} className={styles.card}>
            <h4>{n.issueTitle}</h4>
            <p><strong>Type:</strong> {n.type}</p>
            <p><strong>Old:</strong> {n.oldStatus}</p>
            <p><strong>New:</strong> {n.newStatus}</p>
            <small>{new Date(n.timestamp).toLocaleString()}</small>
          </div>
        ))}
      </div>
    </DashboardLayout>
  );
};

export default Notifications;
