import { NextFunction, Request, Response } from "express";
import HttpError from "../helper/error_handler";

export const errorMiddleware = (
  error: Error,
  req: Request,
  res: Response,
  next: NextFunction
) => {
  if (error instanceof HttpError) {
    res.status(error.status).json({
      status: error.status,
      message: error.message,
      ok: false,
    });
  } else {
    console.log(error);
    res.status(500).json({
      status: 500,
      message: "Internal Server Error",
      ok: false,
    });
  }
};
