import Joi from "joi";

export const productSchema = Joi.object().keys({
  name: Joi.string().required(),
  description: Joi.string().required(),
  originalPrice: Joi.number().required(),
  categoryId: Joi.string().required(),
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

export const updateProductSchema = Joi.object().keys({
  name: Joi.string(),
  description: Joi.string(),
  fit: Joi.string(),
  fabric: Joi.string(),
  discount: Joi.number(),
  originalPrice: Joi.number(),
  stock: Joi.number(),
  categoryId: Joi.string().required(),
});

export const updateSizeSchema = Joi.object().keys({
  title: Joi.string(),
  measurement: Joi.string(),
  description: Joi.string(),
});
