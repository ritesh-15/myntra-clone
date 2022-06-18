import { CreateProductForm } from "../interfaces/product/CreateProductInterface";

class ProductValidation {
  static validateCreateProduct = (
    data: CreateProductForm
  ): CreateProductForm => {
    const errors: CreateProductForm = {
      title: "",
      description: "",
      stocks: "",
      originalPrice: "",
      fit: "",
      fabric: "",
    };

    if (!data.title) {
      errors.title = "Product title not provided";
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
