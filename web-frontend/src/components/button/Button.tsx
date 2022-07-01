import { FC } from "react";
import { Loading, Wrapper } from "./Button.styled";

interface ButtonProps
  extends React.DetailedHTMLProps<
    React.ButtonHTMLAttributes<HTMLButtonElement>,
    HTMLButtonElement
  > {
  title?: string;
  icon?: React.ReactElement;
  disabled?: boolean;
  loading?: boolean;
  outlined?: boolean;
  fullWidth?: boolean;
}

const Button: FC<ButtonProps> = ({
  title,
  icon,
  disabled,
  loading,
  outlined,
  fullWidth = false,
  ...props
}): JSX.Element => {
  return (
    <Wrapper
      icon={icon && true}
      outlined={outlined}
      loading={loading}
      disabled={disabled}
      fullWidth={fullWidth}
    >
      <button {...props}>
        {loading ? (
          <Loading style={{ marginRight: "1em" }} />
        ) : icon ? (
          icon
        ) : (
          ""
        )}
        {title && <span>{title}</span>}
      </button>
    </Wrapper>
  );
};

export default Button;
