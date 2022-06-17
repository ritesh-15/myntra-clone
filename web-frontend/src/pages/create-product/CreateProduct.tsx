import { AddOutlined, DeleteOutline, Save } from "@mui/icons-material";
import { FormControl, InputLabel, MenuItem, Select } from "@mui/material";
import { ChangeEvent, FC, FormEvent, useState } from "react";
import { Button, Input, Textarea } from "../../components";
import {
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
  const [sizes, setSizes] = useState<SizeInterface[]>([
    {
      title: "",
      description: "",
      measurement: "",
    },
  ]);

  const [images, setImages] = useState<File[]>([]);

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
          <label>Product Name</label>
          <Input title="Product Name" />
        </Row>
        <Row>
          <label>Description</label>
          <Textarea title="Description" />
        </Row>

        <Row>
          <label>Price</label>
          <Input title="Price" />
        </Row>

        <Row>
          <label>Fabric</label>

          <Input title="Fabric" />
        </Row>
        <Row>
          <label>Fit</label>

          <Input title="Fit" />
        </Row>
        <Row>
          <label>Category</label>

          <FormControl fullWidth>
            <InputLabel id="demo-simple-select-label">Age</InputLabel>
            <Select
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              label="Age"
            >
              <MenuItem value={10}>Ten</MenuItem>
              <MenuItem value={20}>Twenty</MenuItem>
              <MenuItem value={30}>Thirty</MenuItem>
            </Select>
          </FormControl>
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
          style={{ width: "100%", maxWidth: "200px" }}
          title="Create"
          icon={<Save />}
        />
      </Form>
    </Wrapper>
  );
};

export default CreateProduct;
