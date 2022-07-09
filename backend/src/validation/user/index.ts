import Joi from "joi";

export const updateUserSchema = Joi.object().keys({
  name: Joi.string(),
  phoneNumber: Joi.number(),
});

export const addressSchema = Joi.object().keys({
  address: Joi.string().required(),
  city: Joi.string().required(),
  country: Joi.string().required(),
  pinCode: Joi.number().required(),
  state: Joi.string().required(),
  phoneNumber: Joi.string(),
  nearestLandmark: Joi.string(),
});
