import { SearchOutlined } from "@mui/icons-material";
import styled from "styled-components";

export const Wrapper = styled.div`
  padding: 1em;
  background: ${({ theme }) => theme.colors.background};
`;

export const ProductsWrapper = styled.div`
  background: ${({ theme }) => theme.colors.cardBackground};
`;

export const ProductTopBarWrapper = styled.div`
  margin-bottom: 2em;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;

  @media (min-width: 768px) {
    flex-direction: row;
  }
`;

export const ProductsTopHeading = styled.div`
  h5 {
    font-size: ${({ theme }) => theme.fontSize.h5};
    font-weight: 500;
    color: ${({ theme }) => theme.colors.text};
  }
`;

export const SearchWrapper = styled.div`
  display: flex;
  align-items: center;
  border: 1px solid ${(props) => props.theme.colors.border};
  padding: 0.5em 1em;
  position: relative;
  border-radius: 0.2em;
  margin-right: 1em;

  input {
    border: none;
    padding-left: 0.5em;
    background: transparent;
    color: ${({ theme }) => theme.colors.text};
  }
`;

export const ProductTopBarAction = styled.div`
  display: flex;
`;

export const SearchOutlinedIcon = styled(SearchOutlined)``;
