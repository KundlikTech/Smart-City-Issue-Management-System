import { Navigate } from "react-router-dom";
import { getUser } from "../utils/auth";

const ProtectedRoute = ({ children, roles }) => {
  const user = getUser();

  if (!user) return <Navigate to="/login" />;

  if (roles && !roles.includes(user.role)) return <Navigate to="/login" />;

  return children;
};

export default ProtectedRoute;
