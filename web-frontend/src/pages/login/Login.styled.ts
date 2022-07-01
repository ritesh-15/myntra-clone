import styled from "styled-components";

export const Wrapper = styled.div`
  min-height: 100vh;
  background: ${({ theme }) => theme.colors.background};
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const Container = styled.form`
  width: 95%;
  max-width: 450px;
`;

export const Heading = styled.h4`
  font-size: ${({ theme }) => theme.fontSize.h4};
  color: ${({ theme }) => theme.colors.text};
  margin-bottom: 1em;
`;
