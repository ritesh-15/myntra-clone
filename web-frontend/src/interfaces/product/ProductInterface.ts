export interface ProductInterface {
  name: string;
  description: string;
  originalPrice: number;
  discountPrice: number;
  catagory: ProductCategory;
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
  title: string;
  id: string;
  description: string;
  measurement: string;
}

interface ProductImage {
  url: string;
  publicId: string;
}
