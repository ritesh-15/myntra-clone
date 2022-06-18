import { Alert, Snackbar } from "@mui/material";
import { BrowserRouter, Outlet, Route, Routes } from "react-router-dom";
import styled, { ThemeProvider } from "styled-components";
import { useSnackBar } from "./app/hooks/useSnackBar";
import { Navbar, Sidebar } from "./components";
import GlobalStyle from "./global/GlobalStyle";
import { CreateProduct, Dashboard, Products } from "./pages";
import { DarkTheme } from "./theme/DarkTheme";
import { LightTheme } from "./theme/LightTheme";

const Main = styled.main`
  background: ${(props) => props.theme.background};
  display: grid;
  grid-template-columns: 0.2fr 0.8fr;
`;

const App = () => {
  const { open, message } = useSnackBar();

  return (
    <ThemeProvider theme={LightTheme}>
      <GlobalStyle />
      <Snackbar
        anchorOrigin={{ vertical: "bottom", horizontal: "left" }}
        open={open}
        autoHideDuration={6000}
      >
        <Alert severity="success" sx={{ width: "100%" }}>
          {message}
        </Alert>
      </Snackbar>
      <BrowserRouter>
        <Routes>
          <Route
            path="/"
            element={
              <>
                <Navbar />
                <Main>
                  <Sidebar />
                  <Outlet />
                </Main>
              </>
            }
          >
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
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
};

export default App;
