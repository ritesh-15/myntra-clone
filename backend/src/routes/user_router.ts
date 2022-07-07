import { Router } from "express";
import userController from "../controllers/user_controller";
import { isAdmin } from "../middlewares/admin_middleware";
import { authenticate } from "../middlewares/authenticate_middleware";

const router: Router = Router();

router
  .route("/user/all")
  .get([authenticate, isAdmin], userController.getAllUsers);

router
  .route("/user/:id")
  .get([authenticate], userController.getUser)
  .put([authenticate], userController.updateUser);

router
  .route("/user/makeAdmin/:id")
  .put([authenticate, isAdmin], userController.makeAdmin);

router
  .route("/user/address/all")
  .get(authenticate, userController.getAllAddress);

router.route("/user/add-address").post(authenticate, userController.addAddress);

router
  .route("/user/remove-address/:id")
  .delete(authenticate, userController.removeAddress);

export { router as userRouter };
