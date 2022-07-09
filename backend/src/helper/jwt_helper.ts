import jwt, { JwtPayload } from "jsonwebtoken";
import { ACCESS_TOKEN_SECRET, REFRESH_TOKEN_SECRET } from "../keys/secrets";

interface JwtPayloadInterface extends JwtPayload {
  id: string;
}

class JwtHelper {
  static generateTokens(userId: string) {
    const accessToken = jwt.sign({ id: userId }, ACCESS_TOKEN_SECRET, {
      expiresIn: "1h",
    });
    const refreshToken = jwt.sign({ id: userId }, REFRESH_TOKEN_SECRET, {
      expiresIn: "30d",
    });
    return { accessToken, refreshToken };
  }

  static validateRefreshToken(token: string): JwtPayloadInterface {
    return jwt.verify(token, REFRESH_TOKEN_SECRET) as JwtPayloadInterface;
  }
}

export default JwtHelper;
