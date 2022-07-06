import { api } from "./axios";

class OrderApi {
  static getAllOrders(query?: string, signal?: AbortSignal) {
    return query
      ? api.get(`/order/all?query=${query}`, { signal: signal })
      : api.get("/order/all");
  }

  static getSingleOrder(id: string) {
    return api.get(`/order/${id}`);
  }

  static processOrder(id: string, data: any) {
    return api.put(`/order/${id}`, data);
  }
}

export default OrderApi;
