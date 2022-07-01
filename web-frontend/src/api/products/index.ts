import { api } from "../axios";

class ProductApi {
  static createProduct(data: any) {
    return api.post("/product/create", data);
  }

  static getAllProducts() {
    return api.get("/product/all");
  }
}

export default ProductApi;
