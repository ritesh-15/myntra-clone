import styled from "styled-components";

export const Wrapper = styled.div`
  padding: 1em;
  height: calc(100vh - 60px);
  overflow-y: auto;
  position: relative;
  background-color: ${({ theme }) => theme.colors.background};
`;

export const ProductBasicInfo = styled.div`
  margin-top: 4em;

  h1 {
    font-size: 1.5em;
    font-weight: bold;
    color: ${({ theme }) => theme.colors.text};
    margin-bottom: 2em;
  }
`;

export const Row = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-gap: 1em;
`;

export const Col = styled.div`
  h6 {
    margin-bottom: 0.5em;
  }
`;

export const ProductImages = styled(Row)`
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
`;

export const ProductImage = styled.div`
  width: 100%;
  height: 200px;
  position: relative;
  border-radius: 0.25em;
  overflow: hidden;

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
`;

export const AddImage = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  border: 1px solid ${({ theme }) => theme.colors.borderLight};
  border-radius: 0.25em;
  height: 200px;
  color: ${({ theme }) => theme.colors.text};
  transition: all 250ms ease-in;

  &:hover {
    color: ${({ theme }) => theme.colors.primary};
    border-color: ${({ theme }) => theme.colors.primary};
  }

  label {
    margin-left: 0.5em;
    cursor: pointer;
  }
`;

export const RemoveButton = styled.div`
  position: absolute;
  right: 0;
  z-index: 2;
  background: ${({ theme }) => theme.colors.background};
  padding: 0.5em;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 0.25em 0.25em 0 0.25em;
  bottom: 0;
  cursor: pointer;
  color: ${({ theme }) => theme.colors.primary};
`;

export const SizeTable = styled.div`
  overflow-x: auto;
  border: 1px solid ${({ theme }) => theme.colors.borderLight};
  border-radius: 0.2em;
  box-shadow: -2px 7px 5px 0px ${({ theme }) => theme.colors.shadow};
  margin-bottom: 2em;
  background: ${({ theme }) => theme.colors.cardBackground};
  margin-top: 4em;

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

export const Flex = styled.div`
  display: flex;
  align-items: center;
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
