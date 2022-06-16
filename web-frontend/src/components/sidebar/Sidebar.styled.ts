import { NavLink } from "react-router-dom";
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

  li {
    color: ${({ theme }) => theme.colors.text};

    a {
      display: flex;
      align-items: center;
      padding: 0.75em;
      background-color: ${({ theme }) => theme.colors.background};
      border-radius: 0.25em;
      margin-bottom: 0.75em;
      cursor: pointer;
      transition: background 0.5ms ease-in-out;
      color: inherit;

      &:hover {
        background-color: ${({ theme }) => theme.colors.hoverLight};
      }

      span {
        margin-left: 0.5em;
        color: ${({ theme }) => theme.colors.text};
      }
    }
  }
`;

export const NavLinkStyle = styled(NavLink)`
  &.active {
    color: ${({ theme }) => theme.colors.white} !important;
    background: ${({ theme }) => theme.colors.primary};

    &:hover {
      background: ${({ theme }) => theme.colors.primary};
    }

    span {
      color: ${({ theme }) => theme.colors.white};
    }
  }
`;
