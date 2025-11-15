import styles from "./Header.module.css";
import { getUser } from "../../utils/auth";
import Bell from "../Notifications/Bell";

const Header = () => {
  const user = getUser();

  return (
    <header className={styles.header}>
   <h3>Welcome, {user?.name}</h3>
   <Bell />
</header>
  );
};

export default Header;
