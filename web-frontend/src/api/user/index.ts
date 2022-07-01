import { api } from "../axios";

class UserApi {
  static refresh() {
    return api.get("/auth/refresh");
  }

  static login(data: any) {
    return api.post("/auth/login", data);
  }
}

export default UserApi;
