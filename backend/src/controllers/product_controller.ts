import { NextFunction, Request, Response } from "express";
import CludinaryHelper from "../helper/clodinary_helper";
import HttpError from "../helper/error_handler";
import PrismaClientProvider from "../providers/provide_prism_client";
import fs from "fs";
import { productSchema } from "../validation/product";

interface CreateProductBody {
  name: string;
  description: string;
  originalPrice: number;
  catagory: string;
  fit: string;
  fabric: string;
  sizes: ProductSize[];
}

interface ProductSize {
  title: string;
  measurement: string;
  description: string;
}

class ProductController {
  // @route  POST /api/product/create
  // @desc   Create new product
  // @access Private Admin
  public async createProduct(req: Request, res: Response, next: NextFunction) {
    if (!req.body.data)
      return next(HttpError.unporcessableEntity("Data not found!"));

    const { name, fit, fabric, sizes, catagory, description, originalPrice } =
      JSON.parse(req.body.data) as CreateProductBody;

    try {
      await productSchema.validateAsync(JSON.parse(req.body.data));
    } catch (error: any) {
      return next(HttpError.badRequest(error));
    }

    if (!req.files)
      return next(HttpError.unporcessableEntity("No files uploaded"));

    try {
      // find the catagory
      let catagoryData = await PrismaClientProvider.get().catagory.findUnique({
        where: {
          name: catagory,
        },
      });

      if (catagoryData == null) {
        // create new catagory
        catagoryData = await PrismaClientProvider.get().catagory.create({
          data: {
            name: catagory,
          },
        });
      }

      // create product
      const product = await PrismaClientProvider.get().product.create({
        data: {
          name,
          description,
          originalPrice,
          fit,
          fabric,
          catagoryId: catagoryData.id,
        },
      });

      // create the sizes

      sizes.map(async (size) => {
        await PrismaClientProvider.get().size.create({
          data: {
            title: size.title,
            measurement: size.measurement,
            description: size.description,
            productId: product.id,
          },
        });
      });

      // upload images
      const files = req.files as Express.Multer.File[];

      files.map(async (file) => {
        const uploaded = await CludinaryHelper.uploadImage(file.path);
        fs.unlink(file.path, () => {});
        await PrismaClientProvider.get().image.create({
          data: {
            productId: product.id,
            url: uploaded.secure_url,
            publicId: uploaded.public_id,
          },
        });
      });

      return res.status(201).json({
        ok: true,
        message: "Product created successfully",
        product,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  GET /api/product/all
  // @desc   Gt all products
  // @access Public
  public async getAllProducts(req: Request, res: Response, next: NextFunction) {
    try {
      const products = await PrismaClientProvider.get().product.findMany({
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
      });

      return res.status(200).json({
        ok: true,
        message: "Products fetched successfully",
        products,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }
}

export default new ProductController();
