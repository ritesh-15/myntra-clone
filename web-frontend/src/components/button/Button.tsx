import { Add } from "@mui/icons-material";
import { FC } from "react";
import { Wrapper } from "./Button.styled";

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
}): JSX.Element => {
  return (
    <Wrapper>
      <button>
        {icon && icon}
        <span>{title}</span>
      </button>
    </Wrapper>
  );
};

export default Button;
