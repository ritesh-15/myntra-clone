import { api } from "../axios";

class ProductApi {
  static createProduct(data: any) {
    return api.post("/product/create", data);
  }

  static getAllProducts(page: number, limit: number, query?: string) {
    return query
      ? api.get(
          `/product/all?query=${query}&page=${page}&limit=${limit}&paginate=true`
        )
      : api.get(`/product/all?page=${page}&limit=${limit}&paginate=true`);
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

  static getAllCategories(query?: string) {
    return query
      ? api.get(`/product/category/all?query=${query}`)
      : api.get("/product/category/all");
  }

  static getSingleCategory(id: string, refetch: boolean = false) {
    return api.get(`/product/category/${id}?refetch=${refetch}`);
  }

  static updateCategory(id: string) {
    return api.put("/product/category/:id");
  }

  static createCategory(data: any) {
    return api.post("/product/category/create", data);
  }
}

export default ProductApi;
