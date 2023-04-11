import { config } from "dotenv"
config()

export const SENDGRID_API_KEY: string = process.env.SENDGRID_API_KEY!!
export const ACCESS_TOKEN_SECRET: string = process.env.ACCESS_TOKEN_SECRET!!
export const REFRESH_TOKEN_SECRET: string = process.env.REFRESH_TOKEN_SECRET!!
export const SENDGRID_EMAIL: string = process.env.SENDGRID_EMAIL!!
export const CLOUDINARY_API_SECRET: string = process.env.CLOUDINARY_API_SECRET!!
export const CLOUDINARY_API_KEY: string = process.env.CLOUDINARY_API_KEY!!
export const CLOUDINARY_CLOUD_NAME: string = process.env.CLOUDINARY_CLOUD_NAME!!

export const RAZORPAY_API_KEY: string = process.env.RAZORPAY_API_KEY!!
export const RAZORPAY_KEY_SECRET: string = process.env.RAZORPAY_KEY_SECRET!!

export const REDIS_URL: string = process.env.REDIS_URL!!
export const REDIS_PASSWORD: string = process.env.REDIS_PASSWORD!!
