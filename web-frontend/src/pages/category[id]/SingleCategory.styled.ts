import styled from "styled-components";

export const Wrapper = styled.div`
  padding: 1em;
  height: calc(100vh - 60px);
  overflow-y: auto;
  position: relative;
  background-color: ${({ theme }) => theme.colors.background};
`;

export const Heading = styled.h1`
  font-size: ${({ theme }) => theme.fontSize.h5};
  font-weight: bold;
  color: ${({ theme }) => theme.colors.text};
  display: block;
`;

export const Section = styled.div`
  margin-bottom: 2em;
`;

export const SectionHeading = styled.h5`
  font-weight: 500;
  margin-bottom: 1em;
  padding-bottom: 1em;
  border-bottom: 1px solid ${({ theme }) => theme.colors.borderLight};
  color: ${({ theme }) => theme.colors.text};
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
