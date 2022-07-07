import { Alert, Snackbar } from "@mui/material";
import { useEffect } from "react";
import { BrowserRouter, Outlet, Route, Routes } from "react-router-dom";
import styled, { ThemeProvider } from "styled-components";
import ProductApi from "./api/products";
import { useProducts } from "./app/hooks/useProducts";
import { useSnackBar } from "./app/hooks/useSnackBar";
import { LoadingScreen, Navbar, Sidebar } from "./components";
import GlobalStyle from "./global/GlobalStyle";
import { useRefresh } from "./hooks/useRefresh";
import {
  Categories,
  CreateProduct,
  Dashboard,
  Login,
  Orders,
  Products,
  SingleCategory,
  SingleOrder,
  SingleProduct,
  SingleUser,
  Users,
} from "./pages";
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
  const { open, message, error, handleClose, showSnackbar } = useSnackBar();
  const { changeCategoriesState } = useProducts();

  const fetchCategories = async () => {
    try {
      const { data } = await ProductApi.getAllCategories();
      changeCategoriesState(data.categories);
    } catch (error: any) {
      showSnackbar(error.response.data.message, true);
    }
  };

  // fetch categories
  useEffect(() => {
    fetchCategories();
  }, []);

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
        <Snackbar
          anchorOrigin={{ horizontal: "right", vertical: "bottom" }}
          open={open}
          autoHideDuration={3000}
          onClose={handleClose}
        >
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
              <Route path=":id" element={<SingleProduct />} />
            </Route>

            <Route
              path="users"
              element={
                <>
                  <Outlet />
                </>
              }
            >
              <Route path="" element={<Users />} />
              <Route path=":id" element={<SingleUser />} />
            </Route>

            <Route
              path="categories"
              element={
                <>
                  <Outlet />
                </>
              }
            >
              <Route path="" element={<Categories />} />
              <Route path=":id" element={<SingleCategory />} />
            </Route>

            <Route
              path="orders"
              element={
                <>
                  <Outlet />
                </>
              }
            >
              <Route path="" element={<Orders />} />
              <Route path=":id" element={<SingleOrder />} />
            </Route>
          </Route>

          <Route path="/login" element={<LoginRoute />} />
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
};

export default App;
