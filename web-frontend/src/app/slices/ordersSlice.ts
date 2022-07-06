import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { OrderInterface } from "../../interfaces/OrderInterface";

interface OrderSliceState {
  orders: OrderInterface[];
  isFetch: boolean;
}

const initialState: OrderSliceState = {
  orders: [],
  isFetch: false,
};

const ordersSlice = createSlice({
  name: "order",
  initialState,
  reducers: {
    setOrders: (state, action: PayloadAction<OrderInterface[]>) => {
      state.isFetch = true;
      state.orders = action.payload;
    },
  },
});

export const { setOrders } = ordersSlice.actions;

export default ordersSlice.reducer;
