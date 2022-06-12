import { v2 as cloudinary } from "cloudinary";
import {
  CLOUDINARY_API_KEY,
  CLOUDINARY_API_SECRET,
  CLOUDINARY_CLOUD_NAME,
} from "../keys/secrets";

class CludinaryHelper {
  public static getCludinaryUrl(imageName: string): string {
    return `https://res.cloudinary.com/dvx1jvxzk/image/upload/v1599140078/images/${imageName}`;
  }

  public static async uploadImage(image: any) {
    cloudinary.config({
      cloud_name: CLOUDINARY_CLOUD_NAME,
      api_key: CLOUDINARY_API_KEY,
      api_secret: CLOUDINARY_API_SECRET,
    });

    const result = await cloudinary.uploader.upload(image);
    return result;
  }
}

export default CludinaryHelper;
