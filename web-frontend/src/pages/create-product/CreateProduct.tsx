import { AddOutlined, DeleteOutline, Save } from "@mui/icons-material";
import { ChangeEvent, FC, FormEvent, useEffect, useState } from "react";
import { useSnackBar } from "../../app/hooks/useSnackBar";
import { Button, Input, SelectBox, Textarea } from "../../components";
import { useForm } from "../../hooks/useForm";
import { CreateProductForm } from "../../interfaces/product/CreateProductInterface";
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

const categories = ["Men", "Women"];

const CreateProduct: FC = (): JSX.Element => {
  const [sizes, setSizes] = useState<SizeInterface[]>([
    {
      title: "",
      description: "",
      measurement: "",
    },
  ]);

  const [images, setImages] = useState<File[]>([]);

  const [category, changeCategory] = useState<string>("");

  const { showSnackbar } = useSnackBar();

  // submit form
  const submitForm = (values: CreateProductForm) => {};

  // form state
  const { errors, values, handleChange, handleSubmit } =
    useForm<CreateProductForm>(
      {
        title: "",
        description: "",
        fit: "",
        fabric: "",
        stocks: "",
        originalPrice: "",
      },
      ProductValidation.validateCreateProduct,
      submitForm
    );

  useEffect(() => {
    if (Object(errors).keys !== 0) {
      showSnackbar(errors);
    }
  }, [errors]);

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
            name="title"
            onChange={handleChange}
            value={values.title}
            title="Product Name"
            error={errors.title}
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
            options={categories}
            current={category}
            changeCurrent={(value) => changeCategory(value)}
            label="choose category"
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
