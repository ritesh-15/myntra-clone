import { useDispatch, useSelector } from "react-redux";
import { ProductInterface } from "../../interfaces/product/ProductInterface";
import { setProductsState } from "../slices/productSlice";
import { RootState } from "../store";

export const useProducts = () => {
  const dispatch = useDispatch();
  const { products, isFetch } = useSelector(
    (state: RootState) => state.products
  );

  const changeProductsState = (products: ProductInterface[]) => {
    dispatch(setProductsState(products));
  };

  return { changeProductsState, products, isFetch };
};
