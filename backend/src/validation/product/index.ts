import Joi from "joi";

export const productSchema = Joi.object().keys({
  name: Joi.string().required(),
  description: Joi.string().required(),
  originalPrice: Joi.number().required(),
  catagory: Joi.string().required(),
  fit: Joi.string().required(),
  fabric: Joi.string().required(),
  sizes: Joi.array().items(
    Joi.object().keys({
      title: Joi.string().required(),
      measurement: Joi.string().required(),
      description: Joi.string().required(),
    })
  ),
});
