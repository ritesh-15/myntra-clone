import { ProductInterface } from "./ProductInterface";
import { UserInterface } from "./UserInterface";

export interface OrderInterface {
  id: string;
  userId: string;
  user: UserInterface;
  createdAt: Date;
  updatedAt: Date;
  products: ProductInterface[];
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
  pinCode: number;
  country: string;
  phoneNumber: string | null;
  nearestLandmark: string | null;
  createdAt: Date;
  updatedAt: Date;
}
