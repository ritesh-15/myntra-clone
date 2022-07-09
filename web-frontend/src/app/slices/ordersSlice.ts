import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { OrderInterface } from "../../interfaces/OrderInterface";
import { PaginateState } from "./productSlice";

interface OrderSliceState {
  orders: OrderInterface[];
  isFetch: boolean;
  paginate: {
    next: PaginateState | null;
    previous: PaginateState | null;
  } | null;
}

const initialState: OrderSliceState = {
  orders: [],
  isFetch: false,
  paginate: null,
};

const ordersSlice = createSlice({
  name: "order",
  initialState,
  reducers: {
    setOrders: (state, action: PayloadAction<OrderInterface[]>) => {
      state.isFetch = true;
      state.orders = action.payload;
    },
    setPaginate: (
      state,
      action: PayloadAction<{
        next: PaginateState | null;
        previous: PaginateState | null;
      }>
    ) => {
      state.paginate = action.payload;
    },
  },
});

export const { setOrders, setPaginate } = ordersSlice.actions;

export default ordersSlice.reducer;
