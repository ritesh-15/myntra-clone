import { Prisma } from "@prisma/client";
import { NextFunction, Request, Response } from "express";
import HttpError from "../helper/error_handler";
import { pagination } from "../helper/pagination";
import { UserInterface } from "../interfaces/UserInterface";
import PrismaClientProvider from "../providers/provide_prism_client";
import RedisProvider from "../providers/redis_client";
import { createOrderSchema } from "../validation/order";
import Razorpay from "razorpay";
import { RAZORPAY_API_KEY, RAZORPAY_KEY_SECRET } from "../keys/secrets";

interface CreateOrder {
  products: Product[];
  addressId: string;
  payment: Payment;
  razorPayOrderId: string;
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

interface UpdateOrder {
  orderStatus: string;
}

class OrderController {
  // @route GET /api/order/create
  // @desc Create order
  // @access Private
  public async createOrder(req: Request, res: Response, next: NextFunction) {
    const { products, addressId, payment, razorPayOrderId } =
      req.body as CreateOrder;
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
      await PrismaClientProvider.get().extraOrderInfo.create({
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
      await PrismaClientProvider.get().payment.create({
        data: {
          total: payment.total,
          discount: payment.discount,
          paymentType: payment.paymentType,
          orderId: order.id,
          razorPayOrderId: razorPayOrderId ? razorPayOrderId : null,
        },
      });

      // create order products
      await Promise.all(
        products.map(async (product) => {
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
      return res.status(201).json({
        message: "Order created successfully",
        ok: true,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal server error"));
    }
  }

  // @route POST /api/order/confirm/payment
  // @desc Confirm online payment
  // @access Private

  public async confirmPayment(req: Request, res: Response, next: NextFunction) {
    const { payload, event } = req.body;
    const paymentDetails = payload.payment.entity;

    try {
      const foundPayment = await PrismaClientProvider.get().payment.findFirst({
        where: {
          razorPayOrderId: paymentDetails.order_id,
        },
      });

      if (foundPayment === null) {
        return next(HttpError.notFound("Payment details not found!"));
      }

      const isFailed = event === "payment.failed";

      await PrismaClientProvider.get().payment.update({
        where: {
          id: foundPayment.id,
        },
        data: {
          razorPayPaymentId: paymentDetails.id,
          paymentStatus: isFailed ? false : true,
        },
      });

      return res.json({
        ok: true,
        message: "Payment verified!",
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal server error!"));
    }
  }

  // @route POST /api/order/payment
  // @desc Do the payment
  // @access Private

  public async payment(req: Request, res: Response, next: NextFunction) {
    const { amount } = req.body;

    if (!amount)
      return next(HttpError.unporcessableEntity("Amount is required"));

    const instance = new Razorpay({
      key_id: RAZORPAY_API_KEY,
      key_secret: RAZORPAY_KEY_SECRET,
    });

    try {
      const payment = await instance.orders.create({
        amount: amount * 100,
        currency: "INR",
        receipt: `${Math.floor(Math.random() * 1000000)}`,
      });

      return res.json({
        ok: true,
        message: "Payment created successfully",
        payment,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal server error"));
    }
  }

  // @route GET /api/order/all
  // @desc Get all orders
  // @access Private Admin

  public async getAllOrders(req: Request, res: Response, next: NextFunction) {
    const { page, size, query } = req.query;

    const { skip, limit, result } = pagination(
      page,
      size,
      await PrismaClientProvider.get().order.count()
    );

    const find = query
      ? [
          {
            id: {
              contains: query.toString(),
            },
          },
          {
            userId: {
              contains: query.toString(),
            },
          },
          {
            addressId: {
              contains: query.toString(),
            },
          },
        ]
      : undefined;

    try {
      const orders = await PrismaClientProvider.get().order.findMany({
        where: {
          OR: find,
        },
        include: {
          user: {
            select: {
              id: true,
              name: true,
              email: true,
              phoneNumber: true,
            },
          },
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
        skip: skip,
        take: limit,
      });

      res.status(200).json({
        message: "Orders fetched successfully",
        ok: true,
        orders,
        result,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal server error"));
    }
  }

  // @route GET /api/order/all/user
  // @desc Get all orders
  // @access Private

  public async getAllOrdersByUser(
    req: Request,
    res: Response,
    next: NextFunction
  ) {
    const user = req.user as UserInterface;
    const { page, size, query } = req.query;

    const { skip, limit, result } = pagination(
      page,
      size,
      await PrismaClientProvider.get().order.count()
    );

    try {
      const orders = await PrismaClientProvider.get().order.findMany({
        where: {
          userId: user.id,
        },
        include: {
          user: {
            select: {
              id: true,
              name: true,
              email: true,
              phoneNumber: true,
            },
          },
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
        skip: skip,
        take: limit,
      });

      res.status(200).json({
        message: "Orders fetched successfully",
        ok: true,
        orders,
        result,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal server error"));
    }
  }

  // @route DELETE /api/order/:id
  // @desc Delete order
  // @access Private

  public async deleteOrder(req: Request, res: Response, next: NextFunction) {
    const { id } = req.params;
    const user = req.user as UserInterface;

    if (!id) return next(HttpError.notFound("Id not found"));

    try {
      const foundOrder = await PrismaClientProvider.get().order.findFirst({
        where: {
          AND: [{ id }, { userId: user.id }],
        },
      });

      if (!foundOrder) return next(HttpError.notFound("Order not found"));

      const order = await PrismaClientProvider.get().order.delete({
        where: {
          id,
        },
      });

      return res.status(200).json({
        message: "Order deleted successfully",
        ok: true,
        order,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal server error"));
    }
  }

  // @route PUT /api/order/:id
  // @desc Update order
  // @access Private Admin

  public async updateOrder(req: Request, res: Response, next: NextFunction) {
    const { id } = req.params;
    const user = req.user as UserInterface;
    const { orderStatus } = req.body;

    if (!id) return next(HttpError.notFound("Id not found"));

    if (!orderStatus) return next(HttpError.notFound("Order status not found"));

    try {
      const foundOrder = await PrismaClientProvider.get().order.findFirst({
        where: {
          AND: [{ id }, { userId: user.id }],
        },
      });

      if (!foundOrder) return next(HttpError.notFound("Order not found"));

      const order = await PrismaClientProvider.get().order.update({
        where: {
          id,
        },
        include: {
          extra: true,
          payment: true,
          address: true,
          products: {
            include: {
              product: true,
              size: true,
            },
          },
          user: {
            select: {
              id: true,
              name: true,
              email: true,
              phoneNumber: true,
            },
          },
        },
        data: {
          extra: {
            update: {
              orderStatus,
            },
          },
        },
      });

      await RedisProvider.getInstance().del(order.id);

      await RedisProvider.getInstance().set(order.id, JSON.stringify(order));

      return res.status(200).json({
        message: "Order updated successfully",
        ok: true,
        order,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal server error"));
    }
  }

  // @route GET /api/order/:id
  // @desc Get order
  // @access Private

  public async getOrderById(req: Request, res: Response, next: NextFunction) {
    const { id } = req.params;

    if (!id) return next(HttpError.notFound("Id not found"));

    try {
      const cached = await RedisProvider.getInstance().get(id);

      if (cached) {
        return res.status(200).json({
          message: "Order fetched successfully",
          ok: true,
          order: JSON.parse(cached),
        });
      }

      const foundOrder = await PrismaClientProvider.get().order.findUnique({
        where: {
          id,
        },
        include: {
          extra: true,
          payment: true,
          address: true,
          products: {
            include: {
              product: true,
              size: true,
            },
          },
          user: {
            select: {
              id: true,
              name: true,
              email: true,
              phoneNumber: true,
            },
          },
        },
      });

      if (!foundOrder) return next(HttpError.notFound("Order not found"));

      await RedisProvider.getInstance().set(
        foundOrder.id,
        JSON.stringify(foundOrder)
      );

      return res.status(200).json({
        message: "Order fetched successfully",
        ok: true,
        order: foundOrder,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal server error"));
    }
  }

  // @route GET /api/order/transactions
  // @desc Get transaction
  // @access Private Admin

  public async getTransactions(
    req: Request,
    res: Response,
    next: NextFunction
  ) {
    try {
      const transactions = await PrismaClientProvider.get().payment.findMany({
        orderBy: {
          createdAt: "desc",
        },
        take: 5,
      });

      return res.json({
        ok: true,
        message: "Transactions fetched successfully",
        transactions,
      });
    } catch (error: any) {
      return next(HttpError.internalServerError("Internal server error"));
    }
  }
}

export default new OrderController();
