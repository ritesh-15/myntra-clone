import crypto from "crypto";
import sendgrid from "@sendgrid/mail";
import { SENDGRID_API_KEY, SENDGRID_EMAIL } from "../keys/secrets";

interface OtpData {
  email: string;
  expiresIn?: number;
  otp?: number;
}

class OtpGenerator {
  otp: number;
  email: string;
  expiresIn: number;
  hash: string;
  message: string;

  constructor(data: OtpData) {
    this.email = data.email;
    this.expiresIn = data.expiresIn || Date.now() + 1000 * 60 * 5;
    this.otp =
      data.otp || Math.floor(Math.random() * (999999 - 100000) + 100000);
    this.hash = this.generateHash();
    this.message = `One time password for account verification is ${this.otp}`;
  }

  private generateHash() {
    return crypto
      .createHash("sha256")
      .update(`${this.email}${this.otp}${this.expiresIn}`)
      .digest("hex");
  }

  public async send() {
    sendgrid.setApiKey(SENDGRID_API_KEY);
    return sendgrid.send({
      from: SENDGRID_EMAIL,
      to: this.email,
      subject: "Account Verification!",
      text: this.message,
    });
  }

  static verify(data: OtpData, hash: string) {
    const otpGenerator = new OtpGenerator(data);
    return hash === otpGenerator.hash;
  }
}

export default OtpGenerator;
