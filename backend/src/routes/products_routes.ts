import { Router } from "express";
import productController from "../controllers/product_controller";
import { isAdmin } from "../middlewares/admin_middleware";
import { authenticate } from "../middlewares/authenticate_middleware";
import { upload } from "../middlewares/file_upload";

const router: Router = Router();

router
  .route("/product/create")
  .post([authenticate, isAdmin, upload], productController.createProduct);

router.route("/product/all").get(productController.getAllProducts);

export { router as productRouter };
