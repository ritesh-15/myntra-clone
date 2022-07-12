import { NextFunction, Request, Response } from "express";
import HttpError from "../helper/error_handler";
import { UserInterface } from "../interfaces/UserInterface";
import PrismaClientProvider from "../providers/provide_prism_client";

class WishListController {
  // @route POST /wishlist/add/:id
  // @desc Add item to WishList
  // @access Private

  async addToWishList(req: Request, res: Response, next: NextFunction) {
    // get the product id
    const { id } = req.params;

    const user = req.user as UserInterface;

    if (!id) {
      return next(HttpError.unporcessableEntity("Id not found!"));
    }

    try {
      // find the product
      const product = await PrismaClientProvider.get().product.findUnique({
        where: {
          id,
        },
      });

      if (product == null) {
        return next(HttpError.notFound("Product not found!"));
      }

      // check if already added

      const isAdded = await PrismaClientProvider.get().wishList.findFirst({
        where: {
          AND: [
            {
              userId: user.id,
            },
            {
              productId: product.id,
            },
          ],
        },
      });

      if (isAdded) {
        return next(HttpError.badRequest("Product already added to the cart!"));
      }

      await PrismaClientProvider.get().wishList.create({
        data: {
          userId: user.id,
          productId: product.id,
        },
      });

      return res.status(201).json({
        ok: true,
        message: "Product added to cart successfully!",
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal server error!"));
    }
  }

  // @route DELETE /wishlist/:id
  // @desc Remove from cart
  // @access Private

  async removeFromWishList(req: Request, res: Response, next: NextFunction) {
    const { id } = req.params;

    if (!id) {
      return next(HttpError.unporcessableEntity("Id not found!"));
    }

    try {
      const isFound = await PrismaClientProvider.get().wishList.findUnique({
        where: {
          id,
        },
      });

      if (isFound == null) {
        return next(HttpError.notFound("Product not found in the cart!"));
      }

      await PrismaClientProvider.get().wishList.delete({
        where: {
          id,
        },
      });

      return res.status(200).json({
        ok: true,
        message: "Product removed  successfully!",
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal server error!"));
    }
  }

  // @route DELETE /wishlist/all
  // @desc Get all cart products
  // @access Private
  async getAllWishListProducts(
    req: Request,
    res: Response,
    next: NextFunction
  ) {
    const user = req.user as UserInterface;

    try {
      const cartProducts = await PrismaClientProvider.get().wishList.findMany({
        where: {
          userId: user.id,
        },
        include: {
          product: {
            include: {
              images: {
                select: {
                  url: true,
                  publicId: true,
                },
              },
              sizes: {
                select: {
                  id: true,
                  measurement: true,
                  title: true,
                  description: true,
                },
              },
              catagory: {
                select: {
                  id: true,
                  name: true,
                },
              },
            },
          },
        },
      });

      return res.status(200).json({
        ok: true,
        message: "Product fetched successfully!!",
        products: cartProducts,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal server error!"));
    }
  }
}

export default new WishListController();
