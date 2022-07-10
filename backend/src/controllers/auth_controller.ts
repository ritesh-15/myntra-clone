import { NextFunction, Request, Response } from "express";
import HttpError from "../helper/error_handler";
import OtpGenerator from "../helper/otp_generator";
import PrismaClientProvider from "../providers/provide_prism_client";
import bcrypt from "bcrypt";
import JwtHelper from "../helper/jwt_helper";
import UserDto from "../dtos/UserDto";
import { UserInterface } from "../interfaces/UserInterface";
import RedisProvider from "../providers/redis_client";

interface RegisterBody {
  email: string;
}

interface LoginBody {
  email: string;
  password: string;
}

interface VerifyOtpBody {
  email: string;
  otp: number;
  hash: string;
}

interface ActivateAccountBody {
  password: string;
  name: string;
  phoneNumber: string;
}

interface ForgotPasswordBody {
  email: string;
}

class AuthController {
  // @route  POST /api/auth/register
  // @desc   Register user
  // @access Public
  public async register(req: Request, res: Response, next: NextFunction) {
    const { email } = req.body as RegisterBody;

    if (!email) {
      return next(HttpError.notFound("Phone number not found!"));
    }

    // find user by phone number
    try {
      const user = await PrismaClientProvider.get().user.findUnique({
        where: {
          email,
        },
      });

      if (user != null) {
        return next(HttpError.badRequest("User already exists"));
      }

      console.log(email);

      // create user
      await PrismaClientProvider.get().user.create({
        data: {
          email: email,
          isActive: false,
          isAdmin: false,
          isVerified: false,
          name: "",
          password: "",
          phoneNumber: "",
          resetToken: "",
          resetTokenExpiry: null,
        },
      });

      // create the otp
      const newOtp = new OtpGenerator({
        email: email,
      });

      await newOtp.send();

      return res.status(201).json({
        message: "One time password sent successfully!",
        email: email,
        hash: `${newOtp.hash}.${newOtp.expiresIn}`,
        ok: true,
      });
    } catch (error: any) {
      console.log(error);
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  POST /api/auth/login
  // @desc   Login user
  // @access Public
  public async login(req: Request, res: Response, next: NextFunction) {
    const { email, password } = req.body as LoginBody;

    if (!email || !password) {
      return next(HttpError.badRequest("Missing phone number or password"));
    }

    try {
      // find user by phone number
      const user = await PrismaClientProvider.get().user.findUnique({
        where: {
          email,
        },
      });

      if (user == null) {
        return next(HttpError.badRequest("User does not exist"));
      }

      if (!user.isActive) {
        return next(HttpError.badRequest("User is not active"));
      }

      // check if password matches
      const isAuth = await bcrypt.compare(password, user.password!!);

      if (!isAuth) {
        return next(HttpError.badRequest("Invalide email address or password"));
      }

      // create jwt tokens
      const { accessToken, refreshToken } = JwtHelper.generateTokens(user.id);

      // set refresh token in cache
      await RedisProvider.getInstance().set(user.id, refreshToken);

      res.cookie("accessToken", accessToken, {
        httpOnly: true,
        maxAge: 1000 * 60 * 60 * 24 * 7,
      });

      res.cookie("refreshToken", refreshToken, {
        httpOnly: true,
        maxAge: 1000 * 60 * 60 * 24 * 7,
      });

      return res.status(200).json({
        tokens: {
          accessToken,
          refreshToken,
        },
        user: new UserDto(user),
        ok: true,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  POST /api/auth/verify-otp
  // @desc   Verify otp
  // @access Public
  public async verifyOtp(req: Request, res: Response, next: NextFunction) {
    const { email, otp, hash } = req.body as VerifyOtpBody;

    if (!email || !otp || !hash) {
      return next(HttpError.badRequest("Email or otp or hash not found"));
    }

    try {
      // find user by phone number
      let user = await PrismaClientProvider.get().user.findUnique({
        where: {
          email,
        },
      });

      if (user == null) {
        return next(HttpError.badRequest("User does not exist"));
      }

      // check if otp matches

      const [otpHash, time] = hash.split(".");

      if (parseInt(time) < Date.now()) {
        return next(HttpError.badRequest("Otp is expired"));
      }

      const isOtpValid = OtpGenerator.verify(
        {
          otp: otp,
          email: email,
          expiresIn: parseInt(time),
        },
        otpHash
      );

      if (!isOtpValid) {
        return next(HttpError.badRequest("Invalid otp"));
      }

      user = await PrismaClientProvider.get().user.update({
        where: {
          email,
        },
        data: {
          isVerified: true,
        },
      });

      // create jwt tokens
      const { accessToken, refreshToken } = JwtHelper.generateTokens(user.id);

      // set refresh token in cache
      await RedisProvider.getInstance().set(user.id, refreshToken);

      res.cookie("accessToken", accessToken, {
        httpOnly: true,
        maxAge: 1000 * 60 * 60 * 24 * 7,
      });

      res.cookie("refreshToken", refreshToken, {
        httpOnly: true,
        maxAge: 1000 * 60 * 60 * 24 * 7,
      });

      return res.status(200).json({
        tokens: {
          accessToken,
          refreshToken,
        },
        user: new UserDto(user),
        ok: true,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  PUT /api/auth/activate
  // @desc   Activate account
  // @access Private
  public async activate(req: Request, res: Response, next: NextFunction) {
    const { name, phoneNumber, password } = req.body as ActivateAccountBody;
    const user = req.user as UserInterface;

    if (!name || !phoneNumber || !password) {
      return next(
        HttpError.unporcessableEntity(
          "Missing name or phone number or password"
        )
      );
    }

    try {
      const hashedPassword = await bcrypt.hash(password, 10);

      const updatedUser = await PrismaClientProvider.get().user.update({
        where: {
          id: user.id,
        },
        data: {
          name,
          phoneNumber,
          password: hashedPassword,
          isActive: true,
        },
      });

      return res.status(201).json({
        user: new UserDto(updatedUser),
        ok: true,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  POST /api/auth/resend-otp
  // @desc   Resend Otp
  // @access Public
  public async resendOtp(req: Request, res: Response, next: NextFunction) {
    const { email } = req.body as RegisterBody;

    if (!email) {
      return next(HttpError.notFound("Email address not found!"));
    }

    // find user by email
    try {
      const user = await PrismaClientProvider.get().user.findUnique({
        where: {
          email,
        },
      });

      if (user == null) {
        return next(HttpError.badRequest("User not found!"));
      }

      // create the otp
      const newOtp = new OtpGenerator({
        email: email,
      });

      await newOtp.send();

      return res.status(201).json({
        message: "One time password sent successfully!",
        hash: `${newOtp.hash}.${newOtp.expiresIn}`,
        ok: true,
        email: email,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  GET /api/auth/refresh
  // @desc   Resend Otp
  // @access Public
  public async refresh(req: Request, res: Response, next: NextFunction) {
    let recivedRefreshToken = req.cookies.refreshToken;

    if (!recivedRefreshToken) {
      recivedRefreshToken = req.headers["refreshtoken"];
      if (recivedRefreshToken) {
        recivedRefreshToken = recivedRefreshToken.split(" ")[1];
      }
    }

    try {
      // validate the token with jwt
      const data = JwtHelper.validateRefreshToken(recivedRefreshToken);

      // find the token in cache
      const token = await RedisProvider.getInstance().get(data.id);

      if (token == null)
        return next(HttpError.unauthorized("Session expired!"));

      // generate new tokens
      const { accessToken, refreshToken } = JwtHelper.generateTokens(data.id);

      // revoke previous refresh token
      await RedisProvider.getInstance().del(data.id);

      // set token in redis
      await RedisProvider.getInstance().set(data.id, refreshToken);

      // get user details
      const user = await PrismaClientProvider.get().user.findUnique({
        where: {
          id: data.id,
        },
      });

      if (user == null) return next(HttpError.notFound("User not found!"));

      // set cookies
      res.cookie("accessToken", accessToken, {
        httpOnly: true,
        maxAge: 1000 * 60 * 60 * 24 * 7,
      });

      res.cookie("refreshToken", refreshToken, {
        httpOnly: true,
        maxAge: 1000 * 60 * 60 * 24 * 7,
      });

      return res.status(200).json({
        tokens: {
          accessToken,
          refreshToken,
        },
        ok: true,
        user: new UserDto(user),
      });
    } catch (error) {
      return next(HttpError.unauthorized("Token not found!"));
    }
  }

  // @route  DELETE /api/auth/logout
  // @desc   Log out user
  // @access Private
  public async logout(req: Request, res: Response, next: NextFunction) {
    const user = req.user as UserInterface;

    try {
      // revoke previous tokens
      await RedisProvider.getInstance().del(user.id);

      // clear cookies
      res.clearCookie("accessToken");
      res.clearCookie("refreshToken");

      return res.status(200).json({
        message: "Logout successfully!",
        ok: true,
      });
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }

  // @route  POST /api/auth/forgot-password
  // @desc   Forgot password
  // @access Public

  public async forgotPassword(req: Request, res: Response, next: NextFunction) {
    const { email } = req.body as ForgotPasswordBody;

    if (!email)
      return next(HttpError.unporcessableEntity("Email address not found!"));

    try {
      // find the user
      const user = await PrismaClientProvider.get().user.findUnique({
        where: {
          email,
        },
      });

      if (user == null) return next(HttpError.notFound("User not found!"));

      // create the link

      // TODO
    } catch (error) {
      return next(HttpError.internalServerError("Internal Server Error"));
    }
  }
}

export default new AuthController();
