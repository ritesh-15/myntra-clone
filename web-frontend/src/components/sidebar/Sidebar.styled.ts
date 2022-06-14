import styled from "styled-components";

export const Wrapper = styled.div`
  background: ${(props) => props.theme.colors.background};
  width: 100%;
  height: calc(100vh - 60px);
  padding: 1em;
  border-right: 1px solid ${({ theme }) => theme.colors.borderLight};
  overflow: auto;
`;

export const Title = styled.h1`
  font-size: ${(props) => props.theme.fontSize.h6};
  text-transform: uppercase;
  color: ${(props) => props.theme.colors.text};
`;

export const SidebarItemsList = styled.ul`
  list-style: none;
`;

interface SidebarItemInterface {
  active?: boolean;
}

export const SidebarItem = styled.li<SidebarItemInterface>`
  display: flex;
  align-items: center;
  padding: 0.75em;
  background-color: ${({ theme, active }) =>
    active ? theme.colors.primary : theme.colors.background};
  border-radius: 0.25em;
  margin-bottom: 0.75em;
  cursor: pointer;
  transition: background 0.2s ease-in-out;

  &:hover {
    background-color: ${({ theme, active }) =>
      !active && theme.colors.hoverLight};
  }

  span {
    margin-left: 0.5em;
    color: ${({ theme, active }) =>
      active ? theme.colors.white : theme.colors.text};
  }
`;
