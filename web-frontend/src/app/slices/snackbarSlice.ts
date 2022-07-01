import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface SnackBarState {
  open: boolean;
  message: string;
  error: boolean;
}

const initialState: SnackBarState = {
  open: false,
  message: "",
  error: false,
};

const snackbarSlice = createSlice({
  name: "snackbar",
  initialState,
  reducers: {
    openSnackBar: (state, action: PayloadAction<SnackBarState>) => {
      const { open, message, error } = action.payload;
      state.open = open;
      state.message = message;
      state.error = error || false;
    },
    closeSnackBarState: (state) => {
      state.open = false;
    },
  },
});

export const { openSnackBar, closeSnackBarState } = snackbarSlice.actions;

export default snackbarSlice.reducer;
