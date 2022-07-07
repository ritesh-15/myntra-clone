import { Navigate, Outlet } from "react-router-dom";
import { useUser } from "../app/hooks/useUser";
import { Login } from "../pages";

const LoginRoute = () => {
  const { user } = useUser();

  if (user && user.isAdmin) return <Navigate to={"/"} replace />;

  return <Login />;
};

export default LoginRoute;
