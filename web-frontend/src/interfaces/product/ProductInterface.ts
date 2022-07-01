export interface ProductInterface {
  name: string;
  description: string;
  originalPrice: number;
  discountPrice: number;
  category: ProductCategory;
  fit: string;
  fabric: string;
  images: ProductImage[];
  sizes: ProductSize[];
  discount: number;
  stock: number;
  id: string;
  createdAt: string;
}

interface ProductCategory {
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
