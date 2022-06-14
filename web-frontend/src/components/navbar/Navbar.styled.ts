import { SearchOutlined } from "@mui/icons-material";
import styled from "styled-components";

export const Container = styled.div`
  border-bottom: 1px solid ${(props) => props.theme.colors.borderLight};
  background: ${(props) => props.theme.colors.background};
  height: 60px;
  display: flex;
  justify-content: center;
  width: 100%;
`;

export const Wrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 95%;
  padding: 0.5em 0;
  max-width: 1400px;
`;

export const LogoWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex: 1;

  img {
    width: 50px;
    height: 50px;
    object-fit: contain;
    cursor: pointer;
  }

  h1 {
    margin-left: 1em;
    font-weight: bold;
    font-size: ${(props) => props.theme.fontSize.h6};
    color: ${(props) => props.theme.colors.text};
  }
`;

export const LogoContainer = styled.div`
  display: flex;
  align-items: center;
`;

export const SearchWrapper = styled.div`
  display: flex;
  align-items: center;
  border: 1px solid ${(props) => props.theme.colors.border};
  padding: 0.5em 1em;
  position: relative;
  border-radius: 0.2em;

  input {
    border: none;
    padding-left: 0.5em;
    background: transparent;
    color: ${({ theme }) => theme.colors.text};
  }
`;

export const SearchOutlinedIcon = styled(SearchOutlined)``;
