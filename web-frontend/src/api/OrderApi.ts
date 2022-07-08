import { api } from "./axios";

class OrderApi {
  static getAllOrders(
    page: number,
    limit: number,
    query?: string,
    signal?: AbortSignal
  ) {
    return query
      ? api.get(`/order/all?query=${query}&page=${page}&limit=${limit}`, {
          signal: signal,
        })
      : api.get(`/order/all?page=${page}&limit=${limit}`);
  }

  static getSingleOrder(id: string) {
    return api.get(`/order/${id}`);
  }

  static processOrder(id: string, data: any) {
    return api.put(`/order/${id}`, data);
  }
}

export default OrderApi;
