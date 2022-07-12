import Router from "express";
import wishListController from "../controllers/wishlist_controller";
import { authenticate } from "../middlewares/authenticate_middleware";

const router = Router();

router
  .route("/wishlist/add/:id")
  .post(authenticate, wishListController.addToWishList);

router
  .route("/wishlist/:id")
  .delete(authenticate, wishListController.removeFromWishList);

router
  .route("/wishlist/all")
  .get(authenticate, wishListController.getAllWishListProducts);

export { router as wishListRouter };
