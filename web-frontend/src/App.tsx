import styled, { ThemeProvider } from "styled-components";
import { Navbar, Sidebar } from "./components";
import GlobalStyle from "./global/GlobalStyle";
import { DarkTheme } from "./theme/DarkTheme";
import { LightTheme } from "./theme/LightTheme";

const Main = styled.main`
  background: ${(props) => props.theme.background};
  display: grid;
  grid-template-columns: 0.2fr 0.8fr;
`;

const App = () => {
  return (
    <ThemeProvider theme={DarkTheme}>
      <>
        <GlobalStyle />
        <Navbar />
        <Main>
          <Sidebar />
          <div style={{ background: "hsl(346, 0%, 11%)" }} />
        </Main>
      </>
    </ThemeProvider>
  );
};

export default App;
