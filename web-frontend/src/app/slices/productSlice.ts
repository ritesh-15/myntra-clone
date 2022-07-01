import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { ProductInterface } from "../../interfaces/product/ProductInterface";

interface ProductState {
  products: ProductInterface[];
  isFetch: boolean;
}

const initialState: ProductState = {
  products: [],
  isFetch: false,
};

const productSlice = createSlice({
  name: "product",
  initialState,
  reducers: {
    setProductsState: (state, action: PayloadAction<ProductInterface[]>) => {
      state.products = action.payload;
      state.isFetch = true;
    },
  },
});

export const { setProductsState } = productSlice.actions;

export default productSlice.reducer;
