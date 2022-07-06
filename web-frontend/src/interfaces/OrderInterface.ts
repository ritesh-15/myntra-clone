import { ProductInterface, ProductSize } from "./product/ProductInterface";
import { UserInterface } from "./UserInterface";

interface OrderProductInterface {
  product: ProductInterface;
  quantity: number;
  size: ProductSize;
  id: string;
}

export interface OrderInterface {
  id: string;
  userId: string;
  user: UserInterface;
  createdAt: Date;
  updatedAt: Date;
  products: OrderProductInterface[];
  address: Address;
  addressId: string;
  paymentId: string;
  payment: Payment[];
  extra: ExtraOrderInfo;
  extraOrderInfoId: string;
}

interface ExtraOrderInfo {
  id: string;
  orderStatus: string;
  deliveryCost: number;
  deliveryType: string;
  deliveryDate: Date;
  createdAt: Date;
  updatedAt: Date;
}

interface Payment {
  id: string;
  total: number;
  discount: number;
  paymentType: string;
  paymentStatus: string;
  paymentDate: Date;
  createdAt: Date;
  updatedAt: Date;
}

interface Address {
  id: string;
  address: string;
  city: string;
  state: string;
  pincode: number;
  country: string;
  phoneNumber: string | null;
  nearestLandmark: string | null;
  createdAt: Date;
  updatedAt: Date;
}
