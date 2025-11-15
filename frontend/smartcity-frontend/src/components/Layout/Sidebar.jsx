import { Link } from "react-router-dom";
import styles from "./Sidebar.module.css";
import { getUser, clearAuth } from "../../utils/auth";

const Sidebar = () => {
  const user = getUser();

  return (
    <aside className={styles.sidebar}>
      <h2 className={styles.logo}>SmartCity</h2>

      <nav>
        {/* COMMON LINKS */}
        {user?.role === "USER" && (
          <>
            <Link to="/dashboard">Dashboard</Link>
            <Link to="/user/report">Report Issue</Link>
            <Link to="/user/my-issues">My Issues</Link>
            <Link to="/notifications">Notifications</Link>
            <Link to="/map">Live Map</Link>
          </>
        )}

        {/* ADMIN LINKS */}
        {user?.role === "ADMIN" && (
          <>
            <Link to="/admin/dashboard">Dashboard</Link>
            <Link to="/admin/issues">All Issues</Link>
            <Link to="/admin/assignments">Assignments</Link>
            <Link to="/admin/top-departments">Top Departments</Link>
            <Link to="/admin/sla-breaches">SLA Breaches</Link>
            <Link to="/admin/avg-times">Average Times</Link>
            <Link to="/admin/export">Export Reports</Link>
            <Link to="/admin/map">Live Map</Link>
          </>
        )}
      </nav>

      <button className={styles.logoutBtn} onClick={clearAuth}>
        Logout
      </button>
    </aside>
  );
};

export default Sidebar;
