import { FC, useState } from "react";
import {
  NavLinkStyle,
  SidebarItemsList,
  Title,
  Wrapper,
} from "./Sidebar.styled";
import {
  Dashboard,
  Inventory,
  AddShoppingCart,
  Group,
  SsidChart,
  Notifications,
  Psychology,
  HealthAndSafety,
  Person,
  Logout,
  Settings,
  CategoryOutlined,
} from "@mui/icons-material";
import { useSnackBar } from "../../app/hooks/useSnackBar";
import UserApi from "../../api/user";
import Loader from "../data-loader/Loader";
import { useNavigate } from "react-router-dom";
import { useUser } from "../../app/hooks/useUser";

const Sidebar: FC = (): JSX.Element => {
  const { showSnackbar } = useSnackBar();
  const [loading, setLoading] = useState(false);
  const { removeUserData, user } = useUser();

  const logout = async () => {
    setLoading(true);
    try {
      await UserApi.logout();
      showSnackbar("Logout successful");
      setLoading(false);
      removeUserData();
    } catch (error: any) {
      setLoading(false);
      showSnackbar(error.response.data.message, true);
    }
  };

  return (
    <Wrapper>
      <Title>Main Menu</Title>
      <br />
      <SidebarItemsList>
        <li>
          <NavLinkStyle to={"/"}>
            <Dashboard />
            <span>Dashboard</span>
          </NavLinkStyle>
        </li>
      </SidebarItemsList>

      <Title>List Menu</Title>
      <br />

      <SidebarItemsList>
        <li>
          <NavLinkStyle to={"/products"}>
            <Inventory />
            <span>Products</span>
          </NavLinkStyle>
        </li>
        <li>
          <NavLinkStyle to={"/orders"}>
            <AddShoppingCart />
            <span>Orders</span>
          </NavLinkStyle>
        </li>
        <li>
          <NavLinkStyle to={"/users"}>
            <Group />
            <span>Users</span>
          </NavLinkStyle>
        </li>
        <li>
          <NavLinkStyle to={"/categories"}>
            <CategoryOutlined />
            <span>Categories</span>
          </NavLinkStyle>
        </li>
      </SidebarItemsList>

      <Title>Other Menu</Title>
      <br />

      <SidebarItemsList>
        <li>
          <NavLinkStyle to={"/status"}>
            <SsidChart />
            <span>Status</span>
          </NavLinkStyle>
        </li>
        <li>
          <NavLinkStyle to={"/notifications"}>
            <Notifications />
            <span>Notifications</span>
          </NavLinkStyle>
        </li>
      </SidebarItemsList>

      <Title>Services</Title>
      <br />

      <SidebarItemsList>
        <li>
          <NavLinkStyle to={"/system/health"}>
            <HealthAndSafety />
            <span>System Health</span>
          </NavLinkStyle>
        </li>
        <li>
          <NavLinkStyle to={"/system/logs"}>
            <Psychology />
            <span>Logs</span>
          </NavLinkStyle>
        </li>
        <li>
          <NavLinkStyle to={"/system/setting"}>
            <Settings />
            <span>Settings</span>
          </NavLinkStyle>
        </li>
      </SidebarItemsList>

      <Title>Account</Title>
      <br />

      <SidebarItemsList>
        <li>
          <NavLinkStyle to={`/users/${user?.id}`}>
            <Person />
            <span>Profile</span>
          </NavLinkStyle>
        </li>
        <li>
          {loading ? (
            <Loader />
          ) : (
            <NavLinkStyle to={"/login"} onClick={logout}>
              <Logout />
              <span>Log out</span>
            </NavLinkStyle>
          )}
        </li>
      </SidebarItemsList>
    </Wrapper>
  );
};

export default Sidebar;
