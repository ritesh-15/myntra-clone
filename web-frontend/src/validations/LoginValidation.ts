export const loginValidation = (values: {
  email: string;
  password: string;
}) => {
  const errors: any = {};

  const regex = new RegExp("[a-z0-9]+@[a-z]+.[a-z]{2,3}");

  if (values.email === "") errors.email = "Please enter email address!";
  else if (!regex.test(values.email))
    errors.email = "Email address is not valid";

  if (values.password === "") errors.password = "Please enter password!";

  return errors;
};
