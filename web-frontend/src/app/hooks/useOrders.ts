import { useDispatch, useSelector } from "react-redux";
import { OrderInterface } from "../../interfaces/OrderInterface";
import { setOrders } from "../slices/ordersSlice";
import { RootState } from "../store";

export const useOrders = () => {
  const dispatch = useDispatch();
  const { orders, isFetch } = useSelector((state: RootState) => state.orders);

  const changeOrdersState = (orders: OrderInterface[]) => {
    dispatch(setOrders(orders));
  };

  return { changeOrdersState, orders, isFetch };
};
