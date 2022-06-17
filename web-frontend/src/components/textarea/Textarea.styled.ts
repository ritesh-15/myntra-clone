import styled from "styled-components";

export const Wrapper = styled.div`
  position: relative;
  height: 100%;
  min-height: 100px;
  width: 100%;

  textarea {
    border: none;
    width: 100%;
    outline: none;
    padding-top: 10px;
    padding-left: 14px;
    padding-right: 14px;
    font-size: 1rem;
    resize: none;
    min-height: 100px;
  }

  small {
    font-size: 0.75rem;
    display: block;
    color: ${({ theme }) => theme.colors.red};
  }

  textarea:focus + label,
  textarea:valid + label {
    border-color: ${({ theme }) => theme.colors.primary};
    &::after {
      transform: scaleX(1);
    }
    span {
      transform: translateY(-145%);
      font-size: 0.75em;
      color: ${({ theme }) => theme.colors.primary};
      z-index: 50;
      background: #fff;
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
    border: 1px solid rgba(0, 0, 0, 0.2);
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
      top: 16px;
      transition: all 0.2s ease;
      font-size: 1rem;
      color: ${({ theme }) => theme.colors.textLight};
    }
  }
`;
