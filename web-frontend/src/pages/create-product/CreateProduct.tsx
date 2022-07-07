import { AddOutlined, DeleteOutline, Save } from "@mui/icons-material";
import { ChangeEvent, FC, FormEvent, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import ProductApi from "../../api/products";
import { useProducts } from "../../app/hooks/useProducts";
import { useSnackBar } from "../../app/hooks/useSnackBar";
import { Button, Input, SelectBox, Textarea } from "../../components";
import { useForm } from "../../hooks/useForm";
import { CreateProductForm } from "../../interfaces/product/CreateProductInterface";
import { ProductCategory } from "../../interfaces/product/ProductInterface";
import ProductValidation from "../../validations/ProductValidation";
import {
  FieldHeading,
  Flex,
  Form,
  Heading,
  ImagesTable,
  Label,
  Row,
  SizeTable,
  SubHeading,
  Wrapper,
} from "./CreateProduct.styled";

interface SizeInterface {
  title: string;
  description: string;
  measurement: string;
}

const CreateProduct: FC = (): JSX.Element => {
  // hooks
  const navigate = useNavigate();
  const { showSnackbar } = useSnackBar();
  const { categories } = useProducts();

  // states
  const [sizes, setSizes] = useState<SizeInterface[]>([
    {
      title: "",
      description: "",
      measurement: "",
    },
  ]);

  const [images, setImages] = useState<File[]>([]);

  const [category, changeCategory] = useState<string>("");

  // submit form
  const submitForm = async (values: CreateProductForm) => {
    const choosenCategory: ProductCategory | undefined = categories.find(
      (it) => it.name === category
    );

    const data = {
      sizes,
      categoryId: choosenCategory?.id,
      originalPrice: parseInt(values.originalPrice),
      name: values.name,
      fit: values.fit,
      fabric: values.fabric,
      description: values.description,
    };

    const formData = new FormData();

    images.forEach((image) => {
      formData.append("file", image);
    });

    formData.append("data", JSON.stringify(data));

    try {
      const response = await ProductApi.createProduct(formData);
      showSnackbar(response.data.message);
      navigate("/products", { replace: true });
    } catch (error: any) {
      showSnackbar("Something went wrong!", true);
    }
  };

  // form state
  const { errors, values, handleChange, handleSubmit } =
    useForm<CreateProductForm>(
      {
        name: "",
        description: "",
        fit: "",
        fabric: "",
        originalPrice: "",
      },
      ProductValidation.validateCreateProduct,
      submitForm
    );

  const onChange = (e: ChangeEvent<HTMLInputElement>, index: number) => {
    const data = sizes.map((size, i) => {
      if (i == index) {
        return { ...size, [e.target.name]: e.target.value };
      }

      return size;
    });

    setSizes(data);
  };

  const addMoreRows = (e: FormEvent) => {
    e.preventDefault();

    setSizes([
      ...sizes,
      {
        title: "",
        description: "",
        measurement: "",
      },
    ]);
  };

  const removeRow = (index: number) => {
    setSizes(sizes.filter((data, i) => i != index));
  };

  const chooseImage = (e: ChangeEvent<HTMLInputElement>) => {
    // get current files
    const files = e.target.files;

    if (!files) return;

    const data: File[] = [];

    for (let i = 0; i < files.length; i++) {
      if (files[i].type.includes("image")) {
        data.push(files[i]);
      }
    }

    if (data.length != 0) {
      setImages([...images, ...data]);
    }
  };

  const removeFile = (index: number) => {
    setImages(images.filter((_, i) => i !== index));
  };

  return (
    <Wrapper>
      <Heading>Create A New Product</Heading>
      <Form>
        <Row>
          <FieldHeading>Product Name</FieldHeading>
          <Input
            name="name"
            onChange={handleChange}
            value={values.name}
            title="Product Name"
            error={errors.name}
          />
        </Row>
        <Row>
          <FieldHeading>Description</FieldHeading>
          <Textarea
            name="description"
            onChange={handleChange}
            value={values.description}
            title="Description"
          />
        </Row>

        <Row>
          <FieldHeading>Price</FieldHeading>
          <Input
            name="originalPrice"
            onChange={handleChange}
            value={values.originalPrice}
            title="Price"
            error={errors.originalPrice}
          />
        </Row>

        <Row>
          <FieldHeading>Fabric</FieldHeading>

          <Input
            name="fabric"
            onChange={handleChange}
            value={values.fabric}
            title="Fabric"
            error={errors.fabric}
          />
        </Row>
        <Row>
          <FieldHeading>Fit</FieldHeading>
          <Input
            name="fit"
            onChange={handleChange}
            value={values.fit}
            title="Fit"
            error={errors.fit}
          />
        </Row>
        <Row>
          <FieldHeading>Category</FieldHeading>

          <SelectBox
            options={categories.map((category) => category.name)}
            current={category}
            changeCurrent={(value) => changeCategory(value)}
            label="Choose Category"
          />
        </Row>

        <SubHeading>Sizes</SubHeading>

        <SizeTable>
          <table>
            <thead>
              <tr>
                <th>Title</th>
                <th>Measurement</th>
                <th>Description</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {sizes.map((size, index) => (
                <tr>
                  <td>
                    <input
                      type="text"
                      name="title"
                      onChange={(e) => onChange(e, index)}
                      value={size.title}
                    />
                  </td>
                  <td>
                    <input
                      type="text"
                      name="measurement"
                      onChange={(e) => onChange(e, index)}
                      value={size.measurement}
                    />
                  </td>
                  <td>
                    <input
                      type="text"
                      name="description"
                      onChange={(e) => onChange(e, index)}
                      value={size.description}
                    />
                  </td>
                  <td>
                    <Flex>
                      {index == sizes.length - 1 && (
                        <Button
                          type="button"
                          icon={<AddOutlined />}
                          onClick={addMoreRows}
                        />
                      )}
                      {index != 0 && (
                        <Button
                          outlined
                          icon={<DeleteOutline />}
                          onClick={() => removeRow(index)}
                          type="button"
                        />
                      )}
                    </Flex>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </SizeTable>

        <SubHeading>Images</SubHeading>

        <ImagesTable>
          <table>
            <thead>
              <tr>
                <th>Action</th>
                <th>Image Name</th>
                <th>Image Size</th>
              </tr>
            </thead>
            <tbody>
              {images.length == 0 ? (
                <tr>
                  <td>
                    <Label htmlFor="chooseImage">Choose image</Label>
                    <input
                      id="chooseImage"
                      style={{ display: "none" }}
                      type="file"
                      accept="image/jpeg, image/png, image/jpg"
                      multiple={true}
                      onChange={chooseImage}
                    />
                  </td>
                </tr>
              ) : (
                images.map((image, index) => (
                  <tr>
                    <td>
                      <Flex>
                        {index == images.length - 1 && (
                          <>
                            <Label
                              style={{ marginRight: "1em" }}
                              htmlFor="chooseImage"
                            >
                              Choose more
                            </Label>
                            <input
                              id="chooseImage"
                              style={{ display: "none" }}
                              type="file"
                              accept="image/jpeg, image/png, image/jpg"
                              multiple={true}
                              onChange={chooseImage}
                            />
                          </>
                        )}
                        <Button
                          type="button"
                          icon={<DeleteOutline />}
                          outlined
                          onClick={() => removeFile(index)}
                        />
                      </Flex>
                    </td>
                    <td>{image.name}</td>
                    <td>{image.size}</td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </ImagesTable>

        <br />

        <Button
          type="submit"
          onClick={handleSubmit}
          title="Create"
          icon={<Save />}
        />
      </Form>
    </Wrapper>
  );
};

export default CreateProduct;
