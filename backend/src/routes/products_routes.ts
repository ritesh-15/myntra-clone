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

router
  .route("/product/:id")
  .get(productController.getSingleProduct)
  .put([authenticate, isAdmin], productController.updateProduct)
  .delete([authenticate, isAdmin], productController.deleteProduct);

router.route("/product/category/all").get(productController.getAllCategories);

router
  .route("/product/category/create")
  .post([authenticate, isAdmin], productController.createCategory);

router
  .route("/product/category/:id")
  .put([authenticate, isAdmin], productController.updateCategory)
  .get(productController.getSingleCategory);

router
  .route("/product/size/:id")
  .put([authenticate, isAdmin], productController.updateSize)
  .delete([authenticate, isAdmin], productController.deleteSize);

router
  .route("/product/images/delete")
  .delete([authenticate, isAdmin], productController.deleteProductImage);

router
  .route("/product/images/:productId")
  .put([authenticate, isAdmin, upload], productController.updateProductImage);

export { router as productRouter };
