import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface SnackBarState {
  open: boolean;
  message: string;
}

const initialState: SnackBarState = {
  open: false,
  message: "",
};

const snackbarSlice = createSlice({
  name: "snackbar",
  initialState,
  reducers: {
    openSnackBar: (state, action: PayloadAction<SnackBarState>) => {
      const { open, message } = action.payload;
      state.open = open;
      state.message = message;
    },
  },
});

export const { openSnackBar } = snackbarSlice.actions;

export default snackbarSlice.reducer;
