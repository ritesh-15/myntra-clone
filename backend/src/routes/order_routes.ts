import { Router } from "express";
import orderController from "../controllers/order_controller";
import { isAdmin } from "../middlewares/admin_middleware";
import { authenticate } from "../middlewares/authenticate_middleware";

const router: Router = Router();

router.route("/order/create").post(authenticate, orderController.createOrder);

router
  .route("/order/all")
  .get([authenticate, isAdmin], orderController.getAllOrders);

export { router as orderRouter };
