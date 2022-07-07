import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import {
  ProductCategory,
  ProductInterface,
} from "../../interfaces/product/ProductInterface";

interface ProductState {
  products: ProductInterface[];
  isFetch: boolean;
  productsMap: { [key: string]: ProductInterface };
  currentSetProduct: ProductInterface | null;
  categories: ProductCategory[];
  isCategoriesFetched: boolean;
}

const initialState: ProductState = {
  products: [],
  isFetch: false,
  productsMap: {},
  currentSetProduct: null,
  categories: [],
  isCategoriesFetched: false,
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
    setProductCategories: (state, action: PayloadAction<ProductCategory[]>) => {
      state.categories = action.payload;
    },
  },
});

export const {
  setProductsState,
  setProductInMapById,
  getProductInMapById,
  setProductCategories,
} = productSlice.actions;

export default productSlice.reducer;
