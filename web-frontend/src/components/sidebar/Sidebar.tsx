import { FC } from "react";
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
} from "@mui/icons-material";

const Sidebar: FC = (): JSX.Element => {
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
          <NavLinkStyle to={"/customers"}>
            <Group />
            <span>Users</span>
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
          <NavLinkStyle to={"/account"}>
            <Person />
            <span>Profile</span>
          </NavLinkStyle>
        </li>
        <li>
          <NavLinkStyle to={"/logout"}>
            <Logout />
            <span>Log out</span>
          </NavLinkStyle>
        </li>
      </SidebarItemsList>
    </Wrapper>
  );
};

export default Sidebar;
