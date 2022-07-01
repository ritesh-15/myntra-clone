import { useDispatch, useSelector } from "react-redux";
import { UserInterface } from "../../interfaces/UserInterface";
import { removeUser, setUser } from "../slices/userSlice";
import { RootState } from "../store";

export const useUser = () => {
  const dispatch = useDispatch();
  const { user } = useSelector((state: RootState) => state.user);

  const setUserData = (user: UserInterface) => {
    dispatch(setUser(user));
  };

  const removeUserData = () => {
    dispatch(removeUser());
  };

  return { setUserData, user, removeUserData };
};
