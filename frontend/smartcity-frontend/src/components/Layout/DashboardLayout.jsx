import Sidebar from "./Sidebar";
import Header from "./Header";
import styles from "./DashboardLayout.module.css";

const DashboardLayout = ({ children }) => {
  return (
    <div className={styles.layout}>
      <Sidebar />
      <div className={styles.main}>
        <Header />
        <div className={styles.content}>{children}</div>
      </div>
    </div>
  );
};

export default DashboardLayout;
