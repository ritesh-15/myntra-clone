import { api } from "../axios";

class ProductApi {
  static createProduct(data: any) {
    return api.post("/product/create", data);
  }

  static getAllProducts() {
    return api.get("/product/all");
  }

  static getSingleProduct(id: string) {
    return api.get(`/product/${id}`);
  }

  static deleteProductImage(id: string) {
    return api.delete(`/product/images/${id}`);
  }

  static updateProductImage(id: string, data: any) {
    return api.put(`/product/images/${id}`, data);
  }

  static updateProduct(id: string, data: any) {
    return api.put(`/product/${id}`, data);
  }

  static deleteProduct(id: string) {
    return api.delete(`/product/${id}`);
  }

  static updateSize(id: string, data: any) {
    return api.put(`/product/size/${id}`, data);
  }

  static deleteSize(id: string) {
    return api.delete(`/product/size/${id}`);
  }
}

export default ProductApi;
