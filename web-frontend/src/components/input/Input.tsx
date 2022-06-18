import { FC } from "react";
import { Wrapper } from "./Input.styled";

interface InputProps
  extends React.DetailedHTMLProps<
    React.InputHTMLAttributes<HTMLInputElement>,
    HTMLInputElement
  > {
  error?: string;
  title: string;
}

const Input: FC<InputProps> = (props): JSX.Element => {
  return (
    <Wrapper>
      <input {...props} required />
      <label>
        <span>{props.title}</span>
      </label>
      {props.error && <small>{props.error}</small>}
    </Wrapper>
  );
};

export default Input;
