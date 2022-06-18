import styled from "styled-components";

export const Wrapper = styled.div`
  padding: 1em;
  width: 100%;
  background: ${({ theme }) => theme.colors.background};
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
  margin-bottom: 2em;

  div {
    flex: 1;
  }

  @media (min-width: 768px) {
    flex-direction: row;
    align-items: center;

    div {
      flex: 0.5;
    }
  }
`;

export const Form = styled.form``;

export const SubHeading = styled.h6`
  margin-bottom: 1em;
  font-weight: 500;
  color: ${({ theme }) => theme.colors.text};
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
  margin-bottom: 2em;
  background: ${({ theme }) => theme.colors.cardBackground};

  table {
    border-collapse: collapse;
    width: 100%;
    min-width: 700px;
    overflow: hidden;
    font-family: "Poppins", sans-serif;

    thead {
      box-shadow: -2px 7px 5px 0px ${({ theme }) => theme.colors.shadow};

      th {
        font-weight: 400;
        font-family: inherit;
        text-align: left;
        color: ${({ theme }) => theme.colors.text};
        padding: 0.75em 0.5em;
      }
    }

    tbody {
      td {
        padding: 0.75em 0.5em;
        font-size: 0.85rem;

        input {
          height: 100%;
          padding: 0.5em;
          background: ${({ theme }) => theme.colors.cardBackground};
          border: 1px solid ${({ theme }) => theme.colors.border};
          color: ${({ theme }) => theme.colors.text};
        }
      }
    }
  }
`;

export const ImagesTable = styled.div`
  overflow-x: auto;
  border: 1px solid ${({ theme }) => theme.colors.borderLight};
  border-radius: 0.2em;
  box-shadow: -2px 7px 5px 0px ${({ theme }) => theme.colors.shadow};
  background: ${({ theme }) => theme.colors.cardBackground};

  table {
    border-collapse: collapse;
    width: 100%;
    min-width: 700px;
    overflow: hidden;
    font-family: "Poppins", sans-serif;

    thead {
      box-shadow: -2px 7px 5px 0px ${({ theme }) => theme.colors.shadow};

      th {
        font-weight: 400;
        font-family: inherit;
        text-align: left;
        padding: 0.75em 0.5em;
        color: ${({ theme }) => theme.colors.text};
      }
    }

    tbody {
      td {
        padding: 0.75em 0.5em;
        font-size: 0.85rem;
        color: ${({ theme }) => theme.colors.text};
      }
    }
  }
`;

export const Label = styled.label`
  padding: 0.75em 1em;
  background: ${({ theme }) => theme.colors.cardBackground};
  border: 1px solid ${({ theme }) => theme.colors.primary};
  color: ${({ theme }) => theme.colors.primary};
  margin: 0;
  font-size: 0.85rem;
  border-radius: 0.25em;
  cursor: pointer;
`;

export const Flex = styled.div`
  display: flex;
  align-items: center;
`;

export const FieldHeading = styled.label`
  flex: 1;
  text-align: left;
  display: block;
  color: ${({ theme }) => theme.colors.text};

  @media (min-width: 768px) {
    flex: 0.25;
    text-align: right;
    margin-right: 2em;
  }
`;
