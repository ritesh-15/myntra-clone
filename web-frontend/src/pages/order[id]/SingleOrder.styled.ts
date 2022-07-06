import styled from "styled-components";

export const Wrapper = styled.div`
  padding: 1em;
  height: calc(100vh - 60px);
  overflow-y: auto;
  position: relative;
  background-color: ${({ theme }) => theme.colors.background};
`;

export const Flex = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 2em;
  gap: 1em;

  @media (min-width: 768px) {
    flex-direction: row;
    align-items: center;
  }
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

export const Row = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  grid-gap: 2em;
`;

export const Col = styled.div`
  label {
    margin-bottom: 0.5em;
    display: block;
    font-size: 0.95rem;
    color: ${({ theme }) => theme.colors.text};

    &:last-child {
      color: inherit;
    }
  }

  p {
    background: ${({ theme }) => theme.colors.hoverLight};
    padding: 0.5em;
    color: ${({ theme }) => theme.colors.text};
    border-radius: 0.25em;
  }
`;

export const OrderStatus = styled.span`
  background: ${({ theme }) => theme.colors.background};
  display: block;
  padding: 0.55em;
  border-radius: 0.25em;
  color: ${({ theme }) => theme.colors.primary};
  font-weight: 500;
  border: 1px solid ${({ theme }) => theme.colors.primary};
  max-width: fit-content;
`;

export const SelectWrapper = styled.div`
  label {
    margin-bottom: 0;
    color: ${({ theme }) => theme.colors.text};
  }
`;
