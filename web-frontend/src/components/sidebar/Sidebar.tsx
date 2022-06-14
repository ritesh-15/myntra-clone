import { FC } from "react";
import {
  SidebarItem,
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
        <SidebarItem>
          <Dashboard />
          <span>Dashboard</span>
        </SidebarItem>
      </SidebarItemsList>

      <Title>List Menu</Title>
      <br />

      <SidebarItemsList>
        <SidebarItem active={true}>
          <Inventory style={{ color: "#ffffff" }} />
          <span>Products</span>
        </SidebarItem>
        <SidebarItem>
          <AddShoppingCart />
          <span>Orders</span>
        </SidebarItem>
        <SidebarItem>
          <Group />
          <span>Users</span>
        </SidebarItem>
      </SidebarItemsList>

      <Title>Other Menu</Title>
      <br />

      <SidebarItemsList>
        <SidebarItem>
          <SsidChart />
          <span>Status</span>
        </SidebarItem>
        <SidebarItem>
          <Notifications />
          <span>Notifications</span>
        </SidebarItem>
      </SidebarItemsList>

      <Title>Services</Title>
      <br />

      <SidebarItemsList>
        <SidebarItem>
          <HealthAndSafety />
          <span>System Health</span>
        </SidebarItem>
        <SidebarItem>
          <Psychology />
          <span>Logs</span>
        </SidebarItem>
        <SidebarItem>
          <Settings />
          <span>Settings</span>
        </SidebarItem>
      </SidebarItemsList>

      <Title>Account</Title>
      <br />

      <SidebarItemsList>
        <SidebarItem>
          <Person />
          <span>Profile</span>
        </SidebarItem>
        <SidebarItem>
          <Logout />
          <span>Log out</span>
        </SidebarItem>
      </SidebarItemsList>
    </Wrapper>
  );
};

export default Sidebar;
