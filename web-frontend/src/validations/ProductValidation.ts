import { CreateProductForm } from "../interfaces/product/CreateProductInterface";

class ProductValidation {
  static validateCreateProduct = (
    data: CreateProductForm
  ): CreateProductForm => {
    const errors: any = {};

    if (!data.name) {
      errors.name = "Product title not provided";
    }

    if (!data.description) {
      errors.description = "Product description not provided";
    }

    if (!data.fabric) {
      errors.fabric = "Product fabric not provided";
    }

    if (!data.fit) {
      errors.fit = "Product fit not provided";
    }

    if (!data.originalPrice) {
      errors.originalPrice = "Product price not provided";
    }

    return errors;
  };
}

export default ProductValidation;
