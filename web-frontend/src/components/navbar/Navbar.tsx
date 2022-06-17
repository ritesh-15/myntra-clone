import { FC } from "react";
import {
  Container,
  LogoContainer,
  LogoWrapper,
  SearchOutlinedIcon,
  SearchWrapper,
  Wrapper,
} from "./Navbar.styled";

const Navbar: FC = (): JSX.Element => {
  return (
    <Container>
      <Wrapper>
        <LogoWrapper>
          <LogoContainer>
            <img src="/assets/images/logo.png" alt="logo" />
            <h1>Myntra</h1>
          </LogoContainer>
        </LogoWrapper>
        <SearchWrapper>
          <SearchOutlinedIcon />
          <input type="text" placeholder="Search" />
        </SearchWrapper>
      </Wrapper>
    </Container>
  );
};

export default Navbar;
