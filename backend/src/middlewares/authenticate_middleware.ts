import { Request, Response } from "express";
import passport from "passport";

export const authenticate = passport.authenticate("jwt", {
  session: false,
  failureRedirect: "/auth/fail",
});
