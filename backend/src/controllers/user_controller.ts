import { NextFunction, Request, Response } from "express";
import HttpError from "../helper/error_handler";
import { pagination } from "../helper/pagination";
import { UserInterface } from "../interfaces/UserInterface";
import PrismaClientProvider from "../providers/provide_prism_client";
import RedisProvider from "../providers/redis_client";
import { addressSchema, updateUserSchema } from "../validation/user";

interface UpdateUser {
  name: string;
  phoneNumber: number;
}

interface CreateAddress {
  address: string;
  city: string;
  state: string;
  pinCode: number;
  country: string;
  phoneNumber: string | null;
  nearestLandmark: string | null;
}

class UserController {
  // @route  GET /api/user/all
  // @desc   Get all users
  // @access Private Admin

  public async getAllUsers(req: Request, res: Response, next: NextFunction) {
    const { page, size } = req.query;

    const { skip, limit, result } = pagination(
      page,
      size,
      await PrismaClientProvider.get().user.count()
    );

    try {
      const users = await PrismaClientProvider.get().user.findMany({
        select: {
          id: true,
          name: true,
          email: true,
          phoneNumber: true,
          isAdmin: true,
          isVerified: true,
          isActive: true,
        },
        skip: skip,
        take: limit,
      });

      return res.status(200).json({
        ok: true,
        message: "Users fetched successfully",
        users,
        result,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  PUT /api/user/makeAdmin/:id
  // @desc   Make Admin
  // @access Private Admin

  public async makeAdmin(req: Request, res: Response, next: NextFunction) {
    const { id } = req.params;
    const { isAdmin } = req.body;

    if (!id) {
      return next(HttpError.unporcessableEntity("Id is required"));
    }

    try {
      // find user in db
      const user = await PrismaClientProvider.get().user.findUnique({
        where: { id },
      });

      if (!user) return next(HttpError.notFound("User not found"));

      // update user
      const updatedUser = await PrismaClientProvider.get().user.update({
        where: { id },
        data: {
          isAdmin: isAdmin,
        },
        include: {
          addresses: true,
          orders: true,
        },
      });

      await RedisProvider.getInstance().del(`user-${id}`);

      const sendUser = {
        id: user.id,
        name: user.name,
        email: user.email,
        phoneNumber: user.phoneNumber,
        isAdmin: updatedUser.isAdmin,
        isVerified: user.isVerified,
        isActive: user.isActive,
        address: updatedUser.addresses,
        orders: updatedUser.orders,
      };

      await RedisProvider.getInstance().set(
        `user-${id}`,
        JSON.stringify(sendUser)
      );

      return res.status(200).json({
        ok: true,
        message: "User updated successfully",
        user: sendUser,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  GET /api/user/:id
  // @desc   Get single user
  // @access Private

  public async getUser(req: Request, res: Response, next: NextFunction) {
    const { id } = req.params;

    if (!id) {
      return next(HttpError.unporcessableEntity("Id is required"));
    }

    try {
      // find in cache
      const foundUser = await RedisProvider.getInstance().get(`user-${id}`);

      if (foundUser) {
        return res.status(200).json({
          ok: true,
          message: "User fetched successfully",
          user: JSON.parse(foundUser),
        });
      }

      // find in db
      const user = await PrismaClientProvider.get().user.findUnique({
        where: { id },
        include: {
          addresses: true,
          orders: true,
        },
      });

      if (user == null) {
        return next(HttpError.notFound("User not found"));
      }

      const sendUser = {
        id: user.id,
        name: user.name,
        email: user.email,
        phoneNumber: user.phoneNumber,
        isAdmin: user.isAdmin,
        isVerified: user.isVerified,
        isActive: user.isActive,
        address: user.addresses,
        orders: user.orders,
      };

      // save in cache
      await RedisProvider.getInstance().set(
        `user-${id}`,
        JSON.stringify(sendUser)
      );

      return res.status(200).json({
        ok: true,
        message: "User fetched successfully",
        user: sendUser,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  PUT /api/user/:id
  // @desc   Update profile
  // @access Private

  public async updateUser(req: Request, res: Response, next: NextFunction) {
    const { id } = req.params;

    if (!id) {
      return next(HttpError.unporcessableEntity("Id is required"));
    }

    let data = null;

    try {
      data = await updateUserSchema.validateAsync(req.body);
    } catch (error: any) {
      return next(HttpError.unporcessableEntity(error.message));
    }

    const { name, phoneNumber } = data as UpdateUser;

    try {
      // find user in db
      const user = await PrismaClientProvider.get().user.findUnique({
        where: { id },
      });

      if (!user) return next(HttpError.notFound("User not found"));

      // update user
      const updatedUser = await PrismaClientProvider.get().user.update({
        where: { id },
        data: {
          name,
          phoneNumber: phoneNumber.toString(),
        },
        include: {
          addresses: true,
          orders: true,
        },
      });

      const sendUser = {
        id: updatedUser.id,
        name: updatedUser.name,
        email: updatedUser.email,
        phoneNumber: updatedUser.phoneNumber,
        isAdmin: updatedUser.isAdmin,
        isVerified: updatedUser.isVerified,
        isActive: updatedUser.isActive,
        address: updatedUser.addresses,
        orders: updatedUser.orders,
      };

      await RedisProvider.getInstance().del(`user-${id}`);

      await RedisProvider.getInstance().set(
        `user-${id}`,
        JSON.stringify(sendUser)
      );

      return res.status(200).json({
        ok: true,
        message: "User updated successfully",
        user: sendUser,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  POST /api/user/add-address
  // @desc   Add address
  // @access Private

  public async addAddress(req: Request, res: Response, next: NextFunction) {
    const user = req.user as UserInterface;

    let requestData;

    try {
      requestData = await addressSchema.validateAsync(req.body);
    } catch (error: any) {
      return next(HttpError.unporcessableEntity(error.details));
    }

    const { address, city, nearestLandmark, phoneNumber, state, pinCode } =
      requestData as CreateAddress;

    try {
      const newAddress = await PrismaClientProvider.get().address.create({
        data: {
          address,
          city,
          country: "India",
          state,
          phoneNumber,
          nearestLandmark,
          pincode: pinCode,
          userId: user.id,
        },
      });

      return res.status(200).json({
        ok: true,
        message: "Address added successfully",
        address: newAddress,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  DELETE /api/user/remove-address/:id
  // @desc   Remove address
  // @access Private

  public async removeAddress(req: Request, res: Response, next: NextFunction) {
    const { id } = req.params;

    if (!id) {
      return next(HttpError.unporcessableEntity("Id is required"));
    }

    try {
      const address = await PrismaClientProvider.get().address.delete({
        where: { id },
      });

      if (!address) return next(HttpError.notFound("Address not found"));

      return res.status(200).json({
        ok: true,
        message: "Address removed successfully",
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  GET /api/user/address/all
  // @desc   Get all address
  // @access Private

  public async getAllAddress(req: Request, res: Response, next: NextFunction) {
    const user = req.user as UserInterface;

    try {
      const addresses = await PrismaClientProvider.get().address.findMany({
        where: { userId: user.id },
      });

      return res.status(200).json({
        ok: true,
        message: "Addresses fetched successfully",
        addresses,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }
}

export default new UserController();
