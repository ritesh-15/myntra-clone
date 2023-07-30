import express, { Application, NextFunction, Request, Response } from "express"
import cors from "cors"
import HttpError from "./helper/error_handler"
import { errorMiddleware } from "./middlewares/error_middleware"
import { authRouter } from "./routes/auth_routes"
import helmet from "helmet"
import morgan from "morgan"
import passport from "passport"
import { passportJwt } from "./middlewares/passport-jwt"
import cookieParser from "cookie-parser"
import { productRouter } from "./routes/products_routes"
import { userRouter } from "./routes/user_router"
import RedisProvider from "./providers/redis_client"
import { orderRouter } from "./routes/order_routes"
import PrismaClientProvider from "./providers/provide_prism_client"
import { wishListRouter } from "./routes/wishlist_routes"

const main = async () => {
  try {
    const app: Application = express()

    const PORT = process.env.PORT || 9000

    // redis connection
    const client = RedisProvider.getInstance()

    await Promise.all([PrismaClientProvider.get().$connect(), client.connect()])

    console.log("Database connection established!")
    console.log("Redis connection established!")

    // middlewares

    app.use(express.json())

    app.use(cookieParser())

    app.use(
      cors({
        origin: [
          "http://localhost:3000",
          "https://quiet-liger-ff8153.netlify.app",
        ],
        credentials: true,
        methods: ["GET", "POST", "PUT", "DELETE"],
      })
    )

    app.use(helmet())

    app.use(morgan("dev"))

    passport.initialize()

    passportJwt(passport)

    // routes
    app.use("/api/v1", authRouter)
    app.use("/api/v1", productRouter)
    app.use("/api/v1", userRouter)
    app.use("/api/v1", orderRouter)
    app.use("/api/v1", wishListRouter)

    // passport fail
    app.get("/auth/fail", (req: Request, res: Response, next: NextFunction) => {
      return next(HttpError.unauthorized("Unauthorized!"))
    })

    // error handler
    app.use((req: Request, res: Response, next: NextFunction) => {
      return next(HttpError.notFound("No Route Match!"))
    })

    app.use(errorMiddleware)

    app.listen(PORT, () => {
      console.log(`Server is running on port ${PORT} 🚀`)
    })
  } catch (e: any) {
    console.log(e.message)
    process.exit(1)
  }
}

main()
