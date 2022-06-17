import styled from "styled-components";

export const Wrapper = styled.div`
  position: relative;
  height: 55px;
  width: 100%;
  background: ${({ theme }) => theme.colors.background};

  input {
    border: none;
    height: 100%;
    width: 100%;
    outline: none;
    padding-top: 10px;
    padding-left: 14px;
    padding-right: 14px;
    font-size: 1rem;
    background: transparent;
    color: ${({ theme }) => theme.colors.text};
  }

  small {
    font-size: 0.75rem;
    margin-bottom: 2em;
    display: block;
    color: ${({ theme }) => theme.colors.red};
  }

  input:focus + label,
  input:valid + label {
    border-color: ${({ theme }) => theme.colors.primary};
    &::after {
      transform: scaleX(1);
    }
    span {
      transform: translateY(-155%);
      font-size: 0.75em;
      color: ${({ theme }) => theme.colors.primary};
      z-index: 50;
      background: ${({ theme }) => theme.colors.background};
      width: fit-content;
      padding: 0 0.25em;
    }
  }

  label {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    pointer-events: none;
    height: 100%;
    border: 1px solid ${({ theme }) => theme.colors.borderLight};
    border-radius: 0.25em;
    transition: all 0.5ms ease;

    &::after {
      content: "";
      position: absolute;
      width: 100%;
      height: 100%;
      transition: all 0.5ms ease;
    }
    span {
      position: absolute;
      left: 10px;
      bottom: 16px;
      transition: all 0.2s ease;
      font-size: 1rem;
      color: ${({ theme }) => theme.colors.textLight};
    }
  }
`;
