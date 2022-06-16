import { BrowserRouter, Outlet, Route, Routes } from "react-router-dom";
import styled, { ThemeProvider } from "styled-components";
import { Navbar, Sidebar } from "./components";
import GlobalStyle from "./global/GlobalStyle";
import { Dashboard, Products } from "./pages";
import { DarkTheme } from "./theme/DarkTheme";
import { LightTheme } from "./theme/LightTheme";

const Main = styled.main`
  background: ${(props) => props.theme.background};
  display: grid;
  grid-template-columns: 0.2fr 0.8fr;
`;

const App = () => {
  return (
    <ThemeProvider theme={LightTheme}>
      <GlobalStyle />
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
            <Route path="/products" element={<Products />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
};

export default App;
