import { Router } from "express";
import orderController from "../controllers/order_controller";
import { isAdmin } from "../middlewares/admin_middleware";
import { authenticate } from "../middlewares/authenticate_middleware";

const router: Router = Router();

router.route("/order/create").post(authenticate, orderController.createOrder);

router
  .route("/order/all")
  .get([authenticate, isAdmin], orderController.getAllOrders);

router.route("/order/payment").post([authenticate], orderController.payment);

router
  .route("/order/:id")
  .get(authenticate, orderController.getOrderById)
  .put([authenticate, isAdmin], orderController.updateOrder)
  .delete(authenticate, orderController.deleteOrder);

export { router as orderRouter };
