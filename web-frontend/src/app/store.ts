import { configureStore } from "@reduxjs/toolkit";
import snackbarSlice from "./slices/snackbarSlice";

export const store = configureStore({
  reducer: {
    snackbar: snackbarSlice,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
