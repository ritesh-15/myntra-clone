import Joi from "joi";

export const createOrderSchema = Joi.object({
  products: Joi.array()
    .items(
      Joi.object({
        quantity: Joi.number().required(),
        productId: Joi.string().required(),
        sizeId: Joi.string().required(),
      }).required()
    )
    .required(),
  addressId: Joi.string().required(),
  payment: Joi.object({
    total: Joi.number().required(),
    discount: Joi.number().required(),
    paymentType: Joi.string().required(),
  }).required(),
});
