import styled from "styled-components";

export const Wrapper = styled.div`
  padding: 1em;
  background: ${({ theme }) => theme.colors.background};
  height: calc(100vh - 60px);
  overflow: auto;
`;

export const DashboardCardContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
`;

export const RevenueChart = styled.div`
  background: ${({ theme }) => theme.colors.background};
  padding: 1em;
  margin-top: 2em;
`;

export const Title = styled.h5`
  font-size: ${({ theme }) => theme.fontSize.h5};
  color: ${({ theme }) => theme.colors.text};
`;

export const BottomRow = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  margin-top: 1em;
  flex: 1;

  @media (min-width: 768px) {
    flex-direction: row;
  }
`;

export const LatestTransactions = styled.div`
  flex: 1;

  @media (min-width: 768px) {
    flex: 0.47;
  }
`;

export const NewJoinedUsers = styled.div`
  flex: 1;

  @media (min-width: 768px) {
    flex: 0.47;
  }
`;
