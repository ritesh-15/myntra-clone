import { useDispatch, useSelector } from "react-redux";
import { ProductInterface } from "../../interfaces/product/ProductInterface";
import {
  setProductsState,
  setProductInMapById,
  getProductInMapById,
} from "../slices/productSlice";
import { RootState } from "../store";

export const useProducts = () => {
  const dispatch = useDispatch();
  const { products, isFetch, currentSetProduct } = useSelector(
    (state: RootState) => state.products
  );

  const changeProductsState = (products: ProductInterface[]) => {
    dispatch(setProductsState(products));
  };

  const setProductInMap = (product: ProductInterface) => {
    dispatch(setProductInMapById(product));
  };

  const getProductById = (id: string) => {
    dispatch(getProductInMapById(id));
  };

  return {
    changeProductsState,
    products,
    isFetch,
    setProductInMap,
    getProductById,
    currentSetProduct,
  };
};
