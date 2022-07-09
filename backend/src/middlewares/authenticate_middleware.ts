import { Request, Response } from "express";
import passport from "passport";
import HttpError from "../helper/error_handler";

export const authenticate = passport.authenticate("jwt", {
  session: false,
});
