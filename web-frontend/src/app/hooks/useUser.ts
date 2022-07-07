import { useDispatch, useSelector } from "react-redux";
import { UserInterface } from "../../interfaces/UserInterface";
import { removeUser, setUser, setUsers } from "../slices/userSlice";
import { RootState } from "../store";

export const useUser = () => {
  const dispatch = useDispatch();
  const { user, users, isFetch } = useSelector(
    (state: RootState) => state.user
  );

  const setUserData = (user: UserInterface) => {
    dispatch(setUser(user));
  };

  const removeUserData = () => {
    dispatch(removeUser());
  };

  const changeUsersState = (users: UserInterface[]) => {
    dispatch(setUsers(users));
  };

  return {
    setUserData,
    user,
    removeUserData,
    changeUsersState,
    users,
    isFetch,
  };
};
