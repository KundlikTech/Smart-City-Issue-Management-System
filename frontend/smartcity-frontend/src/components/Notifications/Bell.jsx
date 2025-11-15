import { getNotifications } from "../../utils/notify";
import styles from "./Bell.module.css";
import { Link } from "react-router-dom";

const Bell = () => {
  const count = getNotifications().length;

  return (
    <Link to="/notifications" className={styles.bell}>
      🔔
      {count > 0 && <span className={styles.badge}>{count}</span>}
    </Link>
  );
};

export default Bell;
