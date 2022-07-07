import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { UserInterface } from "../../interfaces/UserInterface";

interface StateInterface {
  user: UserInterface | null;
  users: UserInterface[];
  isFetch: boolean;
}

const initialState: StateInterface = {
  user: null,
  users: [],
  isFetch: false,
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    setUser: (state, action: PayloadAction<UserInterface>) => {
      state.user = action.payload;
    },
    removeUser: (state) => {
      state.user = null;
    },
    setUsers: (state, action: PayloadAction<UserInterface[]>) => {
      state.users = action.payload;
      state.isFetch = true;
    },
  },
});

export const { setUser, removeUser, setUsers } = userSlice.actions;

export default userSlice.reducer;
