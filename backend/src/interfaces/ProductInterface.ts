export interface ProductInterface {
  name: string;
  description: string;
  originalPrice: number;
  discountPrice: number;
  category: ProductCatagory;
  fit: string;
  fabric: string;
  images: ProductImage[];
  sizes: ProductSize[];
  discount: number;
  stock: number;
}

interface ProductCatagory {
  name: string;
  id: string;
  createdAt: Date;
  updatedAt: Date;
}

interface ProductSize {
  name: string;
  id: string;
  createdAt: Date;
  updatedAt: Date;
  productId: string | null;
}

interface ProductImage {
  url: string;
  publicId: string;
}
