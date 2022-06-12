import express, { Application, NextFunction, Request, Response } from "express";
import cors from "cors";
import HttpError from "./helper/error_handler";
import { errorMiddleware } from "./middlewares/error_middleware";
import { authRouter } from "./routes/auth_routes";
import helmet from "helmet";
import morgan from "morgan";
import passport from "passport";
import { passportJwt } from "./middlewares/passport-jwt";
import cookieParser from "cookie-parser";

const app: Application = express();

const PORT = process.env.PORT || 9000;

// middlewares
app.use(express.json());
app.use(cookieParser());
app.use(cors());
app.use(helmet());
app.use(morgan("dev"));
passport.initialize();
passportJwt(passport);

// routes
app.use("/api/v1", authRouter);

// error handler
app.use((req: Request, res: Response, next: NextFunction) => {
  return next(HttpError.notFound("No Route Match!"));
});

app.use(errorMiddleware);

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT} 🚀`);
});
