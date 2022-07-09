import { useDispatch, useSelector } from "react-redux";
import { OrderInterface } from "../../interfaces/OrderInterface";
import { setOrders, setPaginate } from "../slices/ordersSlice";
import { PaginateState } from "../slices/productSlice";
import { RootState } from "../store";

export const useOrders = () => {
  const dispatch = useDispatch();
  const { orders, isFetch, paginate } = useSelector(
    (state: RootState) => state.orders
  );

  const changeOrdersState = (orders: OrderInterface[]) => {
    dispatch(setOrders(orders));
  };

  const changePaginate = (paginate: {
    next: PaginateState | null;
    previous: PaginateState | null;
  }) => {
    dispatch(setPaginate(paginate));
  };

  return { changeOrdersState, orders, isFetch, changePaginate, paginate };
};
