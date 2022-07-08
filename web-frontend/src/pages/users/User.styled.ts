import styled from "styled-components";

export const Wrapper = styled.div`
  padding: 1em;
  background: ${({ theme }) => theme.colors.background};
  width: 100%;
  position: relative;
  height: calc(100vh - 60px);
  overflow-y: auto;
`;

export const Refetch = styled.div`
  position: fixed;
  bottom: 1em;
  right: 1em;
  background: ${({ theme }) => theme.colors.primary};
  color: ${({ theme }) => theme.colors.white};
  padding: 1em;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  z-index: 10;
  cursor: pointer;
  -webkit-box-shadow: -2px 7px 5px 0px ${({ theme }) => theme.colors.shadow};
  -moz-box-shadow: -2px 7px 5px 0px ${({ theme }) => theme.colors.shadow};
  box-shadow: -2px 7px 5px 0px ${({ theme }) => theme.colors.shadow};
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

export const Active = styled.div<{ active?: boolean }>`
  background: ${({ theme, active }) =>
    active ? theme.colors.green : theme.colors.hover};
  width: 15px;
  height: 15px;
  border-radius: 50%;
  margin: 0 auto;
`;

export const LoaderWrapper = styled(Wrapper)`
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const Pagination = styled.div`
  display: flex;
  align-items: center;
  margin-top: 1em;
`;
