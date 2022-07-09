import { NextFunction, Request, Response, Router } from "express";
import authController from "../controllers/auth_controller";
import HttpError from "../helper/error_handler";
import { authenticate } from "../middlewares/authenticate_middleware";

const router: Router = Router();

router.route("/auth/register").post(authController.register);

router.route("/auth/verify-otp").post(authController.verifyOtp);

router.route("/auth/login").post(authController.login);

router.route("/auth/activate").put(authenticate, authController.activate);

router.route("/auth/resend-otp").post(authController.resendOtp);

router.route("/auth/refresh").get(authController.refresh);

router.route("/auth/logout").delete([authenticate], authController.logout);

export { router as authRouter };
