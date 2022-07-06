import { configureStore } from "@reduxjs/toolkit";
import ordersSlice from "./slices/ordersSlice";
import productSlice from "./slices/productSlice";
import snackbarSlice from "./slices/snackbarSlice";
import userSlice from "./slices/userSlice";

export const store = configureStore({
  reducer: {
    snackbar: snackbarSlice,
    user: userSlice,
    products: productSlice,
    orders: ordersSlice,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
