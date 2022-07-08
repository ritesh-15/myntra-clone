import { api } from "../axios";

class UserApi {
  static refresh() {
    return api.get("/auth/refresh");
  }

  static login(data: any) {
    return api.post("/auth/login", data);
  }

  static getAllUsers(page: number = 1, limit: number = 5, query?: string) {
    return query
      ? api.get(`/user/all?query=${query}&page=${page}&size=${limit}`)
      : api.get(`/user/all?page=${page}&size=${limit}`);
  }

  static getSingleUser(id: string) {
    return api.get(`/user/${id}`);
  }

  static makeAdmin(id: string, data: any) {
    return api.put(`/user/makeAdmin/${id}`, data);
  }

  static getAllAddress(id: string) {
    return api.get("/user/address/all");
  }

  static logout() {
    return api.delete("/auth/logout");
  }
}

export default UserApi;
