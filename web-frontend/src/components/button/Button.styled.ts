import styled from "styled-components";

export const Wrapper = styled.div<{
  loading?: boolean;
  disabled?: boolean;
  outlined?: boolean;
  icon?: boolean;
  fullWidth?: boolean;
}>`
  button {
    border: none;
    background: ${({ theme, loading, disabled, outlined }) => {
      if (outlined) return theme.colors.cardBackground;
      if (loading || disabled) return theme.colors.disabled;
      return theme.colors.primary;
    }};
    padding: 0.5em 1em;
    color: ${({ theme, outlined }) => {
      if (outlined) return theme.colors.primary;
      return theme.colors.white;
    }};
    border-radius: 0.25em;
    box-shadow: -2px 7px 5px 0px ${({ theme }) => theme.colors.shadow};
    transition: all 0.2s ease-in-out;
    cursor: ${({ loading, disabled }) => {
      if (loading || disabled) return "default";
      return "pointer";
    }};
    display: flex;
    align-items: center;
    border: 1px solid
      ${({ theme, outlined }) => {
        if (outlined) return theme.colors.primary;
        return "transparent";
      }};
    width: ${({ fullWidth }) => (fullWidth ? "100%" : "fit-content")};
    justify-content: ${({ fullWidth }) => (fullWidth ? "center" : "auto")};

    span {
      margin-left: ${({ icon }) => (icon ? "1em" : "0")};
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
