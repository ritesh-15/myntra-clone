import { Component } from "react";
import { Navigate, Outlet } from "react-router-dom";
import styled from "styled-components";
import { useUser } from "../app/hooks/useUser";
import { Navbar, Sidebar } from "../components";
import { Login } from "../pages";

const Main = styled.main`
  background: ${(props) => props.theme.background};
  display: grid;
  grid-template-columns: 0.2fr 0.8fr;
`;

const ProtectedRoute = () => {
  const { user } = useUser();

  if (!user) return <Navigate to={"/login"} replace />;

  return (
    <>
      <Navbar />
      <Main>
        <Sidebar />
        <Outlet />
      </Main>
    </>
  );
};

export default ProtectedRoute;
