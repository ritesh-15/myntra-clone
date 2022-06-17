import React, { FC } from "react";
import { Wrapper } from "./Textarea.styled";

interface TextareaProps
  extends React.DetailedHTMLProps<
    React.TextareaHTMLAttributes<HTMLTextAreaElement>,
    HTMLTextAreaElement
  > {
  error?: string;
  title: string;
}

const Textarea: FC<TextareaProps> = (props) => {
  return (
    <Wrapper>
      <textarea {...props} autoComplete="off" required />
      <label>
        <span>{props.title}</span>
      </label>
      {props.error && <small>{props.error}</small>}
    </Wrapper>
  );
};

export default Textarea;
