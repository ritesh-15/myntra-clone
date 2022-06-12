import { NextFunction, Request, Response } from "express";
import CludinaryHelper from "../helper/clodinary_helper";
import HttpError from "../helper/error_handler";
import PrismaClientProvider from "../providers/provide_prism_client";

interface CreateProductBody {
  name: string;
  description: string;
  originalPrice: number;
  category: string;
  fit: string;
  fabric: string;
  images: string[];
  sizes: string[];
}

class ProductController {
  // @route  POST /api/product/create
  // @desc   Create new product
  // @access Private Admin

  public async createProduct(req: Request, res: Response, next: NextFunction) {
    const {
      name,
      fit,
      fabric,
      sizes,
      images,
      category,
      description,
      originalPrice,
    } = req.body as CreateProductBody;

    // validate all fields
    if (
      !name ||
      !fit ||
      !fabric ||
      !sizes ||
      !images ||
      !category ||
      !description ||
      !originalPrice
    ) {
      return res.status(400).json({
        message: "All fields are required",
      });
    }

    try {
      // create product
      const product = await PrismaClientProvider.get().product.create({
        data: {
          name,
          description,
          originalPrice,
          fit,
          fabric,
          catagoryId: category,
        },
      });

      // upload images

      await Promise.all(
        images.map(async (image) => {
          const uploaded = await CludinaryHelper.uploadImage(image);
          await PrismaClientProvider.get().image.create({
            data: {
              url: uploaded.secure_url,
              productId: product.id,
              publicId: uploaded.public_id,
            },
          });
        })
      );
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }
}

export default new ProductController();
