import { NextFunction, Request, Response } from "express";
import HttpError from "../helper/error_handler";
import { UserInterface } from "../interfaces/UserInterface";

export const isAdmin = (req: Request, res: Response, next: NextFunction) => {
  const user = req.user as UserInterface;

  if (!user) {
    return next(
      HttpError.unauthorized("You are not authorized to access this resource")
    );
  }

  if (!user.isAdmin) {
    return next(
      HttpError.unauthorized("You are not authorized to access this resource")
    );
  }

  next();
};
