import styled from "styled-components";

export const Wrapper = styled.div`
  padding: 1em;
  border-radius: 0.3em;
  -webkit-box-shadow: -2px 7px 5px 0px ${({ theme }) => theme.colors.shadow};
  -moz-box-shadow: -2px 7px 5px 0px ${({ theme }) => theme.colors.shadow};
  box-shadow: -2px 7px 5px 0px ${({ theme }) => theme.colors.shadow};
  max-width: 95%;
  flex: 1;
  background: ${({ theme }) => theme.colors.cardBackground};
`;

export const TopRow = styled.div<{
  up: boolean;
}>`
  display: flex;
  justify-content: space-between;
  align-items: center;

  span {
    text-transform: uppercase;
    color: ${({ theme }) => theme.colors.text};
  }

  div {
    display: flex;
    align-items: center;
    color: ${({ theme, up }) =>
      up ? theme.colors.green : "hsl(5, 100%, 50%)"};

    span {
      color: inherit;
      margin-left: 0.25em;
    }
  }
`;

export const Title = styled.h1`
  margin: 0.5em 0;
  font-size: ${({ theme }) => theme.fontSize.h5};
  font-weight: 500;
  color: ${({ theme }) => theme.colors.text};
`;

export const BottomRow = styled.div<{
  background: string;
  color: string;
}>`
  display: flex;
  justify-content: space-between;
  align-items: center;

  a {
    color: ${({ theme }) => theme.colors.text};
  }

  div {
    background: ${({ background }) => background};
    padding: 0.2em;
    color: ${({ color }) => color};
    border-radius: 0.25em;
  }
`;
