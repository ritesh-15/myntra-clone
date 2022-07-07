import styled from "styled-components";

export const Wrapper = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  min-height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  z-index: 15;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const Container = styled.div`
  padding: 1em;
  border-radius: 0.25em;
  background-color: ${({ theme }) => theme.colors.cardBackground};

  h1 {
    font-weight: 500;
    font-size: ${({ theme }) => theme.fontSize.h5};
    margin-bottom: 1em;
    color: ${({ theme }) => theme.colors.text};
  }
`;

export const Flex = styled.div`
  display: flex;
  align-items: center;
  margin-top: 1em;
  justify-content: end;
`;
