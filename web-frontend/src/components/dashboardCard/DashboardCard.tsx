import { KeyboardArrowUp, KeyboardArrowDown } from "@mui/icons-material";
import { FC } from "react";
import { Link } from "react-router-dom";
import { BottomRow, Title, TopRow, Wrapper } from "./DashboardCard.styled";

interface DashboardCardProps {
  options: {
    title: string;
    up?: boolean;
    count: number;
    link: string;
    path: string;
    icon: JSX.Element;
    background: string;
    percentage: number;
    color: string;
  };
}

const DashboardCard: FC<DashboardCardProps> = ({ options }): JSX.Element => {
  const { title, up, count, link, path, icon, background, percentage, color } =
    options;

  return (
    <Wrapper>
      <TopRow up={up ? up : false}>
        <span>{title}</span>
        <div>
          {up ? <KeyboardArrowUp /> : <KeyboardArrowDown />}
          <span>{percentage}%</span>
        </div>
      </TopRow>
      <Title>{count}</Title>
      <BottomRow color={color} background={background}>
        <Link to={path}>{link}</Link>
        <div>{icon}</div>
      </BottomRow>
    </Wrapper>
  );
};

export default DashboardCard;
