import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import {
  ProductCategory,
  ProductInterface,
} from "../../interfaces/product/ProductInterface";

export interface PaginateState {
  page: number;
  limit: number;
}
interface ProductState {
  products: ProductInterface[];
  isFetch: boolean;
  productsMap: { [key: string]: ProductInterface };
  currentSetProduct: ProductInterface | null;
  categories: ProductCategory[];
  isCategoriesFetched: boolean;
  paginate: {
    next: PaginateState | null;
    previous: PaginateState | null;
  } | null;
}

const initialState: ProductState = {
  products: [],
  isFetch: false,
  productsMap: {},
  currentSetProduct: null,
  categories: [],
  isCategoriesFetched: false,
  paginate: null,
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
    setPaginateData: (
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

export const {
  setProductsState,
  setProductInMapById,
  getProductInMapById,
  setProductCategories,
  setPaginateData,
} = productSlice.actions;

export default productSlice.reducer;
