import { NextFunction, Request, Response } from "express";
import HttpError from "../helper/error_handler";
import { ProductInterface } from "../interfaces/ProductInterface";
import { UserInterface } from "../interfaces/UserInterface";
import PrismaClientProvider from "../providers/provide_prism_client";
import { createOrderSchema } from "../validation/order";

interface CreateOrder {
  products: Product[];
  addressId: string;
  payment: Payment;
}

interface Payment {
  total: number;
  discount: number;
  paymentType: string;
}

interface Product {
  quantity: number;
  productId: string;
  sizeId: string;
}

class OrderController {
  // @route GET /api/order/create
  // @desc Create order
  // @access Private
  public async createOrder(req: Request, res: Response, next: NextFunction) {
    const { products, addressId, payment } = req.body as CreateOrder;
    const user = req.user as UserInterface;

    // Validate request
    try {
      await createOrderSchema.validateAsync(req.body);
    } catch (error: any) {
      return next(HttpError.unporcessableEntity(error));
    }

    try {
      // create order
      const order = await PrismaClientProvider.get().order.create({
        data: {
          userId: user.id,
          addressId,
        },
      });

      // create extra order details
      const extra = await PrismaClientProvider.get().extraOrderInfo.create({
        data: {
          orderStatus: "pending",
          deliveryCost: 0,
          deliveryDate: new Date(),
          deliveryType: "delivery",
          Order: {
            connect: {
              id: order.id,
            },
          },
          orderId: order.id,
        },
      });

      // create payment
      const paymentId = await PrismaClientProvider.get().payment.create({
        data: {
          total: payment.total,
          discount: payment.discount,
          paymentType: payment.paymentType,
          orderId: order.id,
        },
      });

      // create order products
      await Promise.all(
        products.map(async (product) => {
          const productData =
            await PrismaClientProvider.get().orderProduct.create({
              data: {
                quantity: product.quantity,
                sizeId: product.sizeId,
                productId: product.productId,
                orderId: order.id,
              },
            });
        })
      );

      // send response
      res.status(201).json({
        message: "Order created successfully",
        ok: true,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal server error"));
    }
  }

  // @route GET /api/order/all
  // @desc Get all orders
  // @access Private Admin

  public async getAllOrders(req: Request, res: Response, next: NextFunction) {
    try {
      const orders = await PrismaClientProvider.get().order.findMany({
        include: {
          user: true,
          products: {
            include: {
              product: true,
              size: true,
            },
          },
          extra: true,
          payment: true,
          address: true,
        },
      });

      res.status(200).json({
        message: "Orders fetched successfully",
        ok: true,
        orders,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal server error"));
    }
  }
}

export default new OrderController();
