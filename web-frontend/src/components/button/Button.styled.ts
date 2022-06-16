import styled from "styled-components";

export const Wrapper = styled.div`
  button {
    background: transparent;
    font-family: "Poppins", sans-serif;
    font-size: 1rem;
    border: none;
    background: ${({ theme }) => theme.colors.primary};
    padding: 0.5em 1em;
    color: #fff;
    border-radius: 0.25em;
    box-shadow: -2px 7px 5px 0px ${({ theme }) => theme.colors.shadow};
    transition: all 0.2s ease-in-out;
    cursor: pointer;
    display: flex;
    align-items: center;

    span {
      margin-left: 0.5em;
    }

    &:hover {
      box-shadow: -2px 7px 10px 0px ${({ theme }) => theme.colors.shadowDark};
    }
  }
`;
