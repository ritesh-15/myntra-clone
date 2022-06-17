import styled from "styled-components";

export const Wrapper = styled.div`
  padding: 1em;
  width: 100%;
  background: transparent;
  overflow-y: auto;
  height: calc(100vh - 60px);
  overflow-x: hidden;
`;

export const Heading = styled.h4`
  color: ${({ theme }) => theme.colors.text};
  font-size: ${({ theme }) => theme.fontSize.h4};
  border-bottom: 1px solid ${({ theme }) => theme.colors.borderLight};
  padding-bottom: 0.5em;
  margin-bottom: 1em;
  font-weight: 500;
`;

export const Row = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 1em;

  label {
    flex: 1;
    text-align: left;
    display: block;
  }

  div {
    flex: 1;
  }

  @media (min-width: 768px) {
    flex-direction: row;
    align-items: center;

    label {
      flex: 0.25;
      text-align: right;
      margin-right: 2em;
    }

    div {
      flex: 0.5;
    }
  }
`;

export const Form = styled.form``;

export const SizeHeading = styled.h6`
  margin-bottom: 1em;
`;

export const TableInput = styled.input`
  width: 100%;
  height: 100%;
  border: none;
`;

export const SizeTable = styled.div`
  overflow-x: auto;
  border: 1px solid ${({ theme }) => theme.colors.borderLight};
  border-radius: 0.2em;
  box-shadow: -2px 7px 5px 0px ${({ theme }) => theme.colors.shadow};

  table {
    border-collapse: collapse;
    width: 100%;
    min-width: 700px;
    overflow: hidden;
    font-family: "Poppins", sans-serif;

    thead {
      th {
        font-weight: 400;
        font-family: inherit;
        text-align: left;
        padding: 0.5em;
      }
    }

    tbody {
      td {
        padding: 0.5em;

        input {
          height: 100%;
          padding: 0.5em;
        }
      }
    }
  }
`;
