import { Add } from "@mui/icons-material";
import { FC } from "react";
import { Loading, Wrapper } from "./Button.styled";

interface ButtonProps {
  onClick?: () => void;
  title: string;
  icon?: React.ReactElement;
  disabled?: boolean;
  loading?: boolean;
}

const Button: FC<ButtonProps> = ({
  onClick,
  title,
  icon,
  disabled,
  loading,
}): JSX.Element => {
  return (
    <Wrapper loading={loading} disabled={disabled}>
      <button>
        {!loading && icon ? icon : <Loading />}
        <span>{title}</span>
      </button>
    </Wrapper>
  );
};

export default Button;
