import {
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TableBody,
} from "@mui/material";
import { ChangeEvent, FC, useEffect, useState } from "react";
import { Input, Textarea } from "../../components";
import {
  Form,
  Heading,
  Row,
  SizeHeading,
  SizeTable,
  Wrapper,
} from "./CreateProduct.styled";

const CreateProduct: FC = (): JSX.Element => {
  const [sizeCount, setSizeCount] = useState(1);
  const [sizes, setSizes] = useState([
    {
      title: "",
      description: "",
      measurement: "",
    },
  ]);

  useEffect(() => {
    console.log(sizes);
  }, [sizes]);

  const onChange = (e: ChangeEvent<HTMLInputElement>, index: number) => {
    const data = sizes.find((data, i) => {
      if (i == index) {
        return { ...data, [e.target.name]: e.target.value };
      }
    });

    if (!data) return;

    sizes[index] = data;

    setSizes(sizes);

    console.log(sizes);
  };

  const addMoreRows = () => {
    setSizeCount(sizeCount + 1);
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

        <SizeHeading>Sizes</SizeHeading>

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
                    />
                  </td>
                  <td>
                    <input
                      type="text"
                      name="measurement"
                      onChange={(e) => onChange(e, index)}
                    />
                  </td>
                  <td>
                    <input
                      type="text"
                      name="description"
                      onChange={(e) => onChange(e, index)}
                    />
                  </td>
                  <td>
                    {index == sizes.length - 1 && (
                      <button onClick={addMoreRows}>Add</button>
                    )}
                    {index != 0 && (
                      <button onClick={() => removeRow(index)}>Remove</button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </SizeTable>
      </Form>
    </Wrapper>
  );
};

export default CreateProduct;
