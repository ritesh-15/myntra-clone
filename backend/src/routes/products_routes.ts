import { Router } from "express";

const router: Router = Router();

router.route("/product/create").post();

export { router as productRouter };
