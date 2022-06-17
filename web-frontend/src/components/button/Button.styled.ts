import styled from "styled-components";

export const Wrapper = styled.div<{
  loading?: boolean;
  disabled?: boolean;
}>`
  button {
    border: none;
    background: ${({ theme, loading, disabled }) => {
      if (loading || disabled) return theme.colors.disabled;
      return theme.colors.primary;
    }};
    padding: 0.5em 1em;
    color: #fff;
    border-radius: 0.25em;
    box-shadow: -2px 7px 5px 0px ${({ theme }) => theme.colors.shadow};
    transition: all 0.2s ease-in-out;
    cursor: ${({ loading, disabled }) => {
      if (loading || disabled) return "default";
      return "pointer";
    }};
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

export const Loading = styled.div`
  width: 25px;
  height: 25px;
  border: 2px solid ${({ theme }) => theme.colors.primary};
  border-top: 2px solid white;
  border-left: 2px solid white;
  border-right: 2px solid white;
  animation: spin 1s ease infinite;
  border-radius: 50%;

  @keyframes spin {
    0% {
      transform: rotate(0deg);
    }
    100% {
      transform: rotate(360deg);
    }
  }
`;
