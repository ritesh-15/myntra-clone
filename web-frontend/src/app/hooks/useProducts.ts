import { useDispatch, useSelector } from "react-redux";
import {
  ProductCategory,
  ProductInterface,
} from "../../interfaces/product/ProductInterface";
import {
  setProductsState,
  setProductInMapById,
  getProductInMapById,
  setProductCategories,
} from "../slices/productSlice";
import { RootState } from "../store";

export const useProducts = () => {
  const dispatch = useDispatch();
  const {
    products,
    isFetch,
    currentSetProduct,
    categories,
    isCategoriesFetched,
  } = useSelector((state: RootState) => state.products);

  const changeProductsState = (products: ProductInterface[]) => {
    dispatch(setProductsState(products));
  };

  const setProductInMap = (product: ProductInterface) => {
    dispatch(setProductInMapById(product));
  };

  const getProductById = (id: string) => {
    dispatch(getProductInMapById(id));
  };

  const changeCategoriesState = (categories: ProductCategory[]) => {
    dispatch(setProductCategories(categories));
  };

  return {
    changeProductsState,
    products,
    isFetch,
    setProductInMap,
    getProductById,
    currentSetProduct,
    changeCategoriesState,
    categories,
    isCategoriesFetched,
  };
};
