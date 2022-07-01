import { AxiosError } from "axios";
import React, { FC, useState } from "react";
import UserApi from "../../api/user";
import { useSnackBar } from "../../app/hooks/useSnackBar";
import { useUser } from "../../app/hooks/useUser";
import { Button, Input } from "../../components";
import { useForm } from "../../hooks/useForm";
import { loginValidation } from "../../validations/LoginValidation";
import { Container, Heading, Wrapper } from "./Login.styled";

interface LoginInterface {
  email: string;
  password: string;
}

const Login: FC = (): JSX.Element => {
  const [loading, setLoading] = useState<boolean>(false);
  const { showSnackbar } = useSnackBar();
  const { setUserData } = useUser();

  // submit form
  const submitForm = async (data: LoginInterface) => {
    setLoading(true);
    try {
      const response = await UserApi.login(data);
      if (response.data.ok) {
        setUserData(response.data.user);
      }
      setLoading(false);
    } catch (error: any) {
      showSnackbar(error.response.data.message, true);
      setLoading(false);
    }
  };

  // form state
  const { errors, values, handleChange, handleSubmit } =
    useForm<LoginInterface>(
      {
        email: "",
        password: "",
      },
      loginValidation,
      submitForm
    );

  return (
    <Wrapper>
      <Container>
        <Heading>🔒 Login</Heading>

        <Input
          name="email"
          onChange={handleChange}
          value={values.email}
          title="Email address"
          error={errors.email}
          type="email"
        />

        <br />

        <Input
          name="password"
          onChange={handleChange}
          value={values.password}
          title="Password"
          error={errors.password}
          type="password"
        />

        <br />

        <Button
          loading={loading}
          fullWidth
          type="submit"
          onClick={handleSubmit}
          title="Create"
        />
      </Container>
    </Wrapper>
  );
};

export default Login;
