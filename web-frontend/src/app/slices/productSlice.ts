import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { ProductInterface } from "../../interfaces/product/ProductInterface";

interface ProductState {
  products: ProductInterface[];
  isFetch: boolean;
  productsMap: { [key: string]: ProductInterface };
  currentSetProduct: ProductInterface | null;
}

const initialState: ProductState = {
  products: [],
  isFetch: false,
  productsMap: {},
  currentSetProduct: null,
};

const productSlice = createSlice({
  name: "product",
  initialState,
  reducers: {
    setProductsState: (state, action: PayloadAction<ProductInterface[]>) => {
      state.products = action.payload;
      state.isFetch = true;
    },
    setProductInMapById: (state, action: PayloadAction<ProductInterface>) => {
      state.productsMap[action.payload.id] = action.payload;
    },
    getProductInMapById: (state, action: PayloadAction<string>) => {
      state.currentSetProduct = state.productsMap[action.payload];
    },
  },
});

export const { setProductsState, setProductInMapById, getProductInMapById } =
  productSlice.actions;

export default productSlice.reducer;
