import { Alert, Snackbar } from "@mui/material";
import { BrowserRouter, Outlet, Route, Routes } from "react-router-dom";
import styled, { ThemeProvider } from "styled-components";
import { useSnackBar } from "./app/hooks/useSnackBar";
import { LoadingScreen, Navbar, Sidebar } from "./components";
import GlobalStyle from "./global/GlobalStyle";
import { useRefresh } from "./hooks/useRefresh";
import { CreateProduct, Dashboard, Login, Products } from "./pages";
import LoginRoute from "./routes/LoginRoute";
import ProtectedRoute from "./routes/ProtectedRoute";
import { DarkTheme } from "./theme/DarkTheme";
import { LightTheme } from "./theme/LightTheme";

const Main = styled.main`
  background: ${(props) => props.theme.background};
  display: grid;
  grid-template-columns: 0.2fr 0.8fr;
`;

const App = () => {
  const isLoading = useRefresh();
  const { open, message, error, handleClose } = useSnackBar();

  if (isLoading)
    return (
      <ThemeProvider theme={LightTheme}>
        <LoadingScreen />
      </ThemeProvider>
    );

  return (
    <ThemeProvider theme={LightTheme}>
      <GlobalStyle />
      {open && (
        <Snackbar open={open} autoHideDuration={3000} onClose={handleClose}>
          <Alert severity={error ? "error" : "success"} sx={{ width: "100%" }}>
            {message}
          </Alert>
        </Snackbar>
      )}
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<ProtectedRoute />}>
            <Route path="/" element={<Dashboard />} />
            <Route
              path="products"
              element={
                <>
                  <Outlet />
                </>
              }
            >
              <Route path="create" element={<CreateProduct />} />
              <Route path="" element={<Products />} />
            </Route>
          </Route>

          <Route path="/login" element={<LoginRoute />} />
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
};

export default App;
