import { api } from "../axios";

class ProductApi {
  static createProduct(data: any) {
    return api.post("/product/create", data);
  }

  static getAllProducts(query?: string) {
    return query
      ? api.get(`/product/all?query=${query}`)
      : api.get("/product/all");
  }

  static getSingleProduct(id: string, refetch: boolean = false) {
    return api.get(`/product/${id}?refetch=${refetch}`);
  }

  static deleteProductImage(id: string) {
    return api.delete(`/product/images/delete?publicId=${id}`);
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
