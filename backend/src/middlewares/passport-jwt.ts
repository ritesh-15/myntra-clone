import { Request } from "express";
import passport, { PassportStatic } from "passport";
import { Strategy as JwtStrategy } from "passport-jwt";
import { ACCESS_TOKEN_SECRET } from "../keys/secrets";
import PrismaClientProvider from "../providers/provide_prism_client";

const extractJwt = (req: Request): string => {
  let token = req.headers["authorization"];
  if (token) {
    token = token?.split(" ")[1];
    return token;
  }

  const { accessToken } = req.cookies;
  return accessToken;
};

export const passportJwt = (passport: PassportStatic) => {
  passport.use(
    new JwtStrategy(
      {
        jwtFromRequest: (req) => extractJwt(req),
        secretOrKey: ACCESS_TOKEN_SECRET,
      },
      async (payload, done) => {
        try {
          const user = await PrismaClientProvider.get().user.findUnique({
            where: {
              id: payload.id,
            },
          });

          if (user == null) {
            return done(null, false);
          }

          done(null, user);
        } catch (error) {
          return done(error, false);
        }
      }
    )
  );
};
