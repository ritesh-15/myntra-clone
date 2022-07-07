import { FC, useState } from "react";
import ProductApi from "../../api/products";
import { useSnackBar } from "../../app/hooks/useSnackBar";
import Button from "../button/Button";
import Input from "../input/Input";
import { Container, Flex, Wrapper } from "./CreateCategory.styled";

interface CreateCategoryProps {
  setOpen: (value: boolean) => void;
}

const CreateCategory: FC<CreateCategoryProps> = ({ setOpen }): JSX.Element => {
  const [name, setName] = useState<string>("");
  const { showSnackbar } = useSnackBar();
  const [loading, setLoading] = useState(false);
  const [nameError, setNameError] = useState("");

  const createCategory = async () => {
    if (!name) {
      setNameError("Name is required");
      return;
    }

    setLoading(true);
    try {
      const { data } = await ProductApi.createCategory({ name });
      showSnackbar("Category created successfully");
      setLoading(false);
      setOpen(false);
    } catch (error: any) {
      setLoading(false);
      showSnackbar(error.response.data.message, true);
    }
  };

  return (
    <Wrapper>
      <Container>
        <h1>Create New Category</h1>
        <Input
          value={name}
          onChange={(e) => setName(e.target.value)}
          title="Category Name"
          error={nameError}
        />
        <Flex>
          <Button
            onClick={createCategory}
            style={{ marginRight: "1em" }}
            title="Create"
            loading={loading}
          />
          <Button onClick={() => setOpen(false)} outlined title="Cancel" />
        </Flex>
      </Container>
    </Wrapper>
  );
};

export default CreateCategory;
