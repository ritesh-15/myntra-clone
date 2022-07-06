import { CSSProperties, FC } from "react";
import styled from "styled-components";

interface LoaderInterface {
  style?: CSSProperties;
}

export const Wrapper = styled.div`
  width: 100%;
  height: 100%;
`;

const MainLoader = styled.div`
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 2px solid ${({ theme }) => theme.colors.white};
  border-bottom: 2px solid ${({ theme }) => theme.colors.primary};
  border-top: 2px solid ${({ theme }) => theme.colors.primary};
  border-left: 2px solid ${({ theme }) => theme.colors.primary};
  animation: spin 1s infinite ease;

  @keyframes spin {
    0% {
      transform: rotate(0deg);
    }
    100% {
      transform: rotate(360deg);
    }
  }
`;

const Loader: FC<LoaderInterface> = ({ style }) => {
  return <MainLoader />;
};

export default Loader;
