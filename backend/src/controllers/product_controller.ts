import { NextFunction, Request, Response } from "express";
import CludinaryHelper from "../helper/clodinary_helper";
import HttpError from "../helper/error_handler";
import PrismaClientProvider from "../providers/provide_prism_client";
import fs from "fs";
import {
  productSchema,
  updateProductSchema,
  updateSizeSchema,
} from "../validation/product";
import RedisProvider from "../providers/redis_client";
import { CATEGORIES } from "../constants";
import { pagination } from "../helper/pagination";
import { UserInterface } from "../interfaces/UserInterface";

interface CreateProductBody {
  name: string;
  description: string;
  originalPrice: number;
  categoryId: string;
  fit: string;
  fabric: string;
  sizes: ProductSize[];
}

interface UpdateProduct {
  name: string;
  description: string;
  originalPrice: number;
  fit: string;
  fabric: string;
  discount: number;
  stock: number;
  categoryId: string;
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

    const { name, fit, fabric, sizes, categoryId, description, originalPrice } =
      JSON.parse(req.body.data) as CreateProductBody;

    try {
      await productSchema.validateAsync(JSON.parse(req.body.data));
    } catch (error: any) {
      return next(HttpError.badRequest(error));
    }

    if (!req.files)
      return next(HttpError.unporcessableEntity("No files uploaded"));

    try {
      // create product
      const product = await PrismaClientProvider.get().product.create({
        data: {
          name,
          description,
          originalPrice,
          fit,
          fabric,
          catagoryId: categoryId,
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

      console.log(req.files);
      console.log(files);

      await Promise.all(
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
        })
      );

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
  // @desc   Get all products
  // @access Public

  public async getAllProducts(req: Request, res: Response, next: NextFunction) {
    const { query, page, size } = req.query;

    const { skip, limit, result } = pagination(
      page,
      size,
      await PrismaClientProvider.get().product.count()
    );

    let find: any = null;
    if (query) {
      const search = query.toString();
      find = [
        {
          name: {
            contains: search,
          },
        },
        {
          description: {
            contains: search,
          },
        },
        {
          catagoryId: {
            contains: search,
          },
        },
      ];
    }

    try {
      const products = await PrismaClientProvider.get().product.findMany({
        where: {
          OR: find ? find : undefined,
        },
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
        skip: skip,
        take: limit,
      });

      return res.status(200).json({
        ok: true,
        message: "Products fetched successfully",
        products,
        result,
      });
    } catch (error) {
      console.log(error);
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  GET /api/product/:id
  // @desc   Get single product
  // @access Public

  public async getSingleProduct(
    req: Request,
    res: Response,
    next: NextFunction
  ) {
    const { id } = req.params;
    const { refetch } = req.query;
    let user = req.user;

    console.log(user);

    if (!id) return next(HttpError.unporcessableEntity("Id not found!"));

    try {
      // find product in cache
      const foundInCache = await RedisProvider.getInstance().get(id);

      if (foundInCache && refetch === "false") {
        return res.status(200).json({
          ok: true,
          message: "Product fetched successfully",
          product: JSON.parse(foundInCache),
        });
      }

      const product = await PrismaClientProvider.get().product.findUnique({
        where: {
          id,
        },
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

      if (product == null)
        return next(HttpError.notFound("Product not found!"));

      // set product in cache
      await RedisProvider.getInstance().set(id, JSON.stringify(product));

      return res.status(200).json({
        ok: true,
        message: "Product fetched successfully",
        product,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  PUT /api/product/:id
  // @desc   Update product
  // @access Private Admin

  public async updateProduct(req: Request, res: Response, next: NextFunction) {
    const { id } = req.params;

    if (!id) return next(HttpError.unporcessableEntity("Id not found!"));

    if (!req.body)
      return next(HttpError.unporcessableEntity("Data not found!"));

    let data;

    try {
      data = (await updateProductSchema.validateAsync(
        req.body
      )) as UpdateProduct;
    } catch (error) {
      return next(HttpError.unporcessableEntity("Invalid data"));
    }

    const {
      name,
      fit,
      fabric,
      description,
      originalPrice,
      discount,
      stock,
      categoryId,
    } = data;

    try {
      const product = await PrismaClientProvider.get().product.findUnique({
        where: {
          id,
        },
      });

      if (product == null)
        return next(HttpError.notFound("Product not found!"));

      let discountPrice: number = 0;

      if (discount && discount != 0) {
        discountPrice =
          (originalPrice || product.originalPrice) -
          ((originalPrice || product.originalPrice) * discount) / 100;
      }

      const updatedProduct = await PrismaClientProvider.get().product.update({
        where: {
          id,
        },
        data: {
          name,
          description,
          originalPrice,
          fit,
          fabric,
          discount,
          discountPrice: parseInt(discountPrice.toString()),
          stock,
          catagoryId: categoryId,
        },
        include: {
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
          images: {
            select: {
              url: true,
              publicId: true,
            },
          },
        },
      });

      await RedisProvider.getInstance().del(id);

      await RedisProvider.getInstance().set(id, JSON.stringify(updatedProduct));

      return res.status(200).json({
        ok: true,
        message: "Product updated successfully",
        product: updatedProduct,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  DELETE /api/product/images/delete
  // @desc   Delete product image
  // @access Private Admin

  public async deleteProductImage(
    req: Request,
    res: Response,
    next: NextFunction
  ) {
    let { publicId } = req.query;

    publicId = publicId?.toString();

    if (!publicId) return next(HttpError.unporcessableEntity("Id not found!"));

    try {
      const image = await PrismaClientProvider.get().image.findUnique({
        where: {
          publicId: publicId,
        },
      });

      if (image == null) return next(HttpError.notFound("Image not found!"));

      // remove from store

      await CludinaryHelper.deleteImage(image.publicId);

      await PrismaClientProvider.get().image.delete({
        where: {
          publicId: image.publicId,
        },
      });

      return res.status(200).json({
        ok: true,
        message: "Image deleted successfully",
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  PUT /api/product/images/:productId
  // @desc   Update product image
  // @access Private Admin

  public async updateProductImage(
    req: Request,
    res: Response,
    next: NextFunction
  ) {
    const { productId } = req.params;

    if (!productId) return next(HttpError.unporcessableEntity("Id not found!"));

    if (!req.files)
      return next(HttpError.unporcessableEntity("Image not found!"));

    try {
      const images = req.files as Express.Multer.File[];

      const product = await PrismaClientProvider.get().product.findUnique({
        where: {
          id: productId,
        },
      });

      if (product == null)
        return next(HttpError.notFound("Product not found!"));

      // upload images to cloudinary
      images.map(async (image) => {
        const uploaded = await CludinaryHelper.uploadImage(image.path);

        fs.unlink(image.path, () => {});

        await PrismaClientProvider.get().image.create({
          data: {
            url: uploaded.secure_url,
            publicId: uploaded.public_id,
            product: {
              connect: {
                id: product.id,
              },
            },
          },
        });
      });

      return res.status(200).json({
        ok: true,
        message: "Image uploaded successfully",
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  DELETE /api/product/:id
  // @desc   Delete product
  // @access Private Admin

  public async deleteProduct(req: Request, res: Response, next: NextFunction) {
    const { id } = req.params;

    if (!id) return next(HttpError.unporcessableEntity("Id not found!"));

    try {
      const product = await PrismaClientProvider.get().product.findUnique({
        where: {
          id,
        },
      });

      if (product == null)
        return next(HttpError.notFound("Product not found!"));

      // remove the images from database and store
      const images = await PrismaClientProvider.get().image.findMany({
        where: {
          productId: product.id,
        },
      });

      await Promise.all(
        images.map(async (image) => {
          await CludinaryHelper.deleteImage(image.publicId);
          await PrismaClientProvider.get().image.delete({
            where: {
              id: image.id,
            },
          });
        })
      );

      // remove the sizes from database
      await PrismaClientProvider.get().size.deleteMany({
        where: {
          productId: product.id,
        },
      });

      await PrismaClientProvider.get().product.delete({
        where: {
          id,
        },
      });

      return res.status(200).json({
        ok: true,
        message: "Product deleted successfully",
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  PUT /api/product/category/:id
  // @desc   Update category
  // @access Private

  public async updateCategory(req: Request, res: Response, next: NextFunction) {
    const { id } = req.params;

    if (!id) return next(HttpError.unporcessableEntity("Id not found!"));

    const { name } = req.body;

    try {
      const category = await PrismaClientProvider.get().catagory.findUnique({
        where: {
          id,
        },
      });

      if (category == null)
        return next(HttpError.notFound("Category not found!"));

      const updatedCategory = await PrismaClientProvider.get().catagory.update({
        where: {
          id,
        },
        data: {
          name,
        },
      });

      return res.status(200).json({
        ok: true,
        message: "Category updated successfully",
        category: updatedCategory,
      });
    } catch (error) {
      return next(HttpError.unporcessableEntity("Invalid data"));
    }
  }

  // @route  POST /api/product/category/create
  // @desc   Create category
  // @access Private Admin

  public async createCategory(req: Request, res: Response, next: NextFunction) {
    const { name } = req.body;

    if (!name) return next(HttpError.unporcessableEntity("Name not found!"));

    try {
      const isExists = await PrismaClientProvider.get().catagory.findUnique({
        where: {
          name,
        },
      });

      if (isExists) {
        return next(HttpError.unporcessableEntity("Category already exists!"));
      }

      const category = await PrismaClientProvider.get().catagory.create({
        data: {
          name,
        },
      });

      return res.status(200).json({
        ok: true,
        message: "Category created successfully",
        category,
      });
    } catch (error) {
      return next(HttpError.unporcessableEntity("Invalid data"));
    }
  }

  // @route  GET /api/product/category/all
  // @desc   Get all categories
  // @access Public

  public async getAllCategories(
    req: Request,
    res: Response,
    next: NextFunction
  ) {
    try {
      const categories = await PrismaClientProvider.get().catagory.findMany({
        select: {
          id: true,
          name: true,
          createdAt: true,
        },
      });

      return res.status(200).json({
        ok: true,
        message: "Categories fetched successfully",
        categories,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  GET /api/product/category/:id
  // @desc   Get single category
  // @access Public

  public async getSingleCategory(
    req: Request,
    res: Response,
    next: NextFunction
  ) {
    const { id } = req.params;
    const { refetch } = req.query;

    if (!id) return next(HttpError.unporcessableEntity("Id not found!"));

    try {
      // find in cache
      const foundCategory = await RedisProvider.getInstance().get(id);

      if (foundCategory && refetch === "false") {
        return res.status(200).json({
          ok: true,
          category: JSON.parse(foundCategory),
          message: "Category fetched successfully",
        });
      }

      const category = await PrismaClientProvider.get().catagory.findUnique({
        where: {
          id,
        },
        include: {
          Product: {
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

      if (category == null)
        return next(HttpError.notFound("Category not found!"));

      // set in cache
      await RedisProvider.getInstance().set(
        category.id,
        JSON.stringify(category)
      );

      return res.status(200).json({
        ok: true,
        message: "Category fetched successfully",
        category,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  PUT /api/product/size/:id
  // @desc   Update product size
  // @access Private Admin

  public async updateSize(req: Request, res: Response, next: NextFunction) {
    const { id } = req.params;

    if (!id) return next(HttpError.unporcessableEntity("Id not found!"));

    const { measurement, title, description } = req.body;

    try {
      await updateSizeSchema.validateAsync(req.body);
    } catch (error) {
      return next(HttpError.unporcessableEntity("Invalid data"));
    }

    try {
      const size = await PrismaClientProvider.get().size.findUnique({
        where: {
          id,
        },
      });

      if (size == null) return next(HttpError.notFound("Size not found!"));

      const updatedSize = await PrismaClientProvider.get().size.update({
        where: {
          id,
        },
        data: {
          measurement,
          title,
          description,
        },
      });

      return res.status(200).json({
        ok: true,
        message: "Size updated successfully",
        size: updatedSize,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  DELETE /api/product/size/:id
  // @desc   Delete product size
  // @access Private Admin

  public async deleteSize(req: Request, res: Response, next: NextFunction) {
    const { id } = req.params;

    if (!id) return next(HttpError.unporcessableEntity("Id not found!"));

    try {
      const size = await PrismaClientProvider.get().size.findUnique({
        where: {
          id,
        },
      });

      if (size == null) return next(HttpError.notFound("Size not found!"));

      await PrismaClientProvider.get().size.delete({
        where: {
          id,
        },
      });

      return res.status(200).json({
        ok: true,
        message: "Size deleted successfully",
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }
}

export default new ProductController();
