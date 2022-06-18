import { KeyboardArrowDown } from "@mui/icons-material";
import styled from "styled-components";

interface OptionContainerProps {
  active: boolean;
  search?: boolean;
}

export const StyledBox = styled.div`
  position: relative;
`;

export const Selected = styled.div`
  border: 1px solid ${({ theme }) => theme.colors.borderLight};
  padding: 1em;
  border-radius: 0.25em;
  cursor: pointer;
  user-select: none;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

export const OptionsContainer = styled.div<OptionContainerProps>`
  position: absolute;
  box-shadow: 0 0 7px ${({ theme }) => theme.colors.shadowDark};
  width: 100%;
  left: 0;
  right: 0;
  border-radius: 0.25em;
  z-index: 120;
  background: ${({ theme }) => theme.colors.background};
  margin-top: 0.5em;
  max-height: 300px;
  overflow-y: auto;
  visibility: ${({ active }) => (!active ? "hidden" : "auto")};
  transition: opacity 0.3s ease;
  opacity: ${({ active }) => (!active ? "0" : "1")};
  top: 0em;

  div {
    padding: 1em;
    cursor: pointer;
    &:first-child {
      background: ${({ search }) =>
        search ? "rgba(0, 0, 0, 0.025)" : "transparent"};
    }
    input {
      border: none;
      outline: none;
      width: 100%;
      height: 100%;
      cursor: pointer;
      background: transparent;
      color: ${({ theme }) => theme.colors.text};
    }
    &:hover {
      background: rgba(0, 0, 0, 0.025);
      color: ${({ theme }) => theme.colors.primary};
    }
  }
`;

export const Arrow = styled(KeyboardArrowDown)<OptionContainerProps>`
  color: rgba(0, 0, 0, 0.5);
  transform: ${({ active }) => (active ? "rotate(180deg)" : "rotate(0)")};
`;
