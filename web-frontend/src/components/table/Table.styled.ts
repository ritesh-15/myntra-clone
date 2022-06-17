import styled from "styled-components";

export const TableContainer = styled.div`
  overflow-x: auto;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
  border-radius: 8px;
  background: ${({ theme }) => theme.colors.cardBackground};
  width: 100%;

  &::-webkit-scrollbar {
    height: 7px;
    background: rgba(0, 0, 0, 0.1);
  }
  &::-webkit-scrollbar-thumb {
    background: rgba(0, 0, 0, 0.1);
    border-radius: 8px;
    &:hover {
      background: rgba(0, 0, 0, 0.2);
    }
  }
`;

export const TableWrapper = styled.table`
  width: 100%;
  border-collapse: collapse;
  font-family: inherit;
  box-shadow: -2px 7px 5px 0px ${({ theme }) => theme.colors.shadow};
  border-radius: 0.25em;
  min-width: 700px;
  overflow: hidden;
`;

export const TD = styled.td`
  padding: 1em;
  font-family: "Poppins", sans-serif;
  text-align: right;
  font-size: 0.8rem;
  color: ${({ theme }) => theme.colors.text};
`;

export const TableBody = styled.tbody``;

export const TR = styled.tr`
  &:nth-child(even) {
    background-color: ${({ theme }) => theme.colors.hoverLight};
  }
`;

export const TableHead = styled.thead`
  tr {
    box-shadow: -2px 7px 5px 0px ${({ theme }) => theme.colors.shadow};
  }

  th {
    font-family: "Poppins", sans-serif;
    padding: 0.75em 1em;
    text-align: right;
    font-weight: 500;
    color: ${({ theme }) => theme.colors.text};
  }
`;
