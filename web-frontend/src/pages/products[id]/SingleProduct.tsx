import { ChangeEvent, FC, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Button, Input, Loader, Textarea } from "../../components";
import { ProductInterface } from "../../interfaces/product/ProductInterface";
import {
  AddImage,
  Col,
  Flex,
  ProductBasicInfo,
  ProductImage,
  ProductImages,
  RemoveButton,
  Row,
  SizeTable,
  Wrapper,
} from "./SingleProduct.styled";
import ProductApi from "../../api/products";
import { useSnackBar } from "../../app/hooks/useSnackBar";
import { AddOutlined, DeleteOutline, Save } from "@mui/icons-material";

interface SizeInterface {
  title: string;
  description: string;
  measurement: string;
}

const SingleProduct: FC = (): JSX.Element => {
  // hooks
  const { id } = useParams();
  const { showSnackbar } = useSnackBar();

  //states
  const [product, setProduct] = useState<ProductInterface | null>(null);
  const [loading, setLoading] = useState(false);
  const [images, setImages] = useState<File[]>([]);
  const [sizes, setSizes] = useState<SizeInterface[]>([
    {
      title: "",
      description: "",
      measurement: "",
    },
  ]);

  useEffect(() => {
    (async () => {
      if (!id) return;

      setLoading(true);
      try {
        const { data } = await ProductApi.getSingleProduct(id);
        console.log(data);
        if (data.ok) {
          setProduct(data.product);
          setSizes(data.product.sizes);
        }
        showSnackbar("Product fetched successfully");
        setLoading(false);
      } catch (error: any) {
        showSnackbar(error.response.data.message, true);
        setLoading(false);
      }
    })();
  }, [id]);

  // remove the image
  const removeFile = (index: number) => {
    setImages(images.filter((_, i) => i !== index));
  };

  // choose the image
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

  // change the size
  const onChange = (e: ChangeEvent<HTMLInputElement>, index: number) => {
    const data = sizes.map((size, i) => {
      if (i == index) {
        return { ...size, [e.target.name]: e.target.value };
      }

      return size;
    });

    setSizes(data);
  };

  // add more row to table
  const addMoreRows = () => {
    setSizes([
      ...sizes,
      {
        title: "",
        description: "",
        measurement: "",
      },
    ]);
  };

  // remove the size from table
  const removeRow = (index: number) => {
    setSizes(sizes.filter((data, i) => i != index));
  };

  if (loading) return <Loader />;

  return (
    <Wrapper>
      <ProductImages>
        {product?.images.map((image) => {
          return (
            <ProductImage key={image.publicId}>
              <img src={image.url} alt="" />
              <RemoveButton>
                <DeleteOutline />
              </RemoveButton>
            </ProductImage>
          );
        })}
        {images.map((image, index) => {
          return (
            <ProductImage key={index}>
              <img src={URL.createObjectURL(image)} alt="" />
              <RemoveButton onClick={() => removeFile(index)}>
                <DeleteOutline />
              </RemoveButton>
            </ProductImage>
          );
        })}
        <AddImage>
          <AddOutlined />
          <label htmlFor="chooseImage">Add Images</label>
          <input
            id="chooseImage"
            style={{ display: "none" }}
            type="file"
            accept="image/jpeg, image/png, image/jpg"
            multiple={true}
            onChange={chooseImage}
          />
        </AddImage>
      </ProductImages>

      <ProductBasicInfo>
        <h1>{product?.id}</h1>
        <Row>
          <Col>
            <h6>Product Name</h6>
            <Input value={product?.name} title="Product Name" />
          </Col>
          <Col>
            <h6>Product Category</h6>
            <Input value={product?.catagory?.name} title="Product Category" />
          </Col>

          <Col>
            <h6>Price</h6>
            <Input value={product?.originalPrice} title="Price" />
          </Col>
          <Col>
            <h6>Discount</h6>
            <Input value={product?.discount} title="Discount" />
          </Col>
          <Col>
            <h6>Fabric</h6>
            <Input value={product?.fabric} title="Fabric" />
          </Col>
          <Col>
            <h6>Fit</h6>
            <Input value={product?.fit} title="Fit" />
          </Col>
          <Col>
            <h6>Stocks</h6>
            <Input value={product?.stock} title="Stocks" />
          </Col>
          <Col>
            <h6>Product Description</h6>
            <Textarea
              value={product?.description}
              title="Product Description"
            />
          </Col>
        </Row>
      </ProductBasicInfo>

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
                    onChange={(e) => onChange(e, index)}
                    type="text"
                    name="title"
                    value={size.title}
                  />
                </td>
                <td>
                  <input
                    onChange={(e) => onChange(e, index)}
                    type="text"
                    name="measurement"
                    value={size.measurement}
                  />
                </td>
                <td>
                  <input
                    onChange={(e) => onChange(e, index)}
                    type="text"
                    name="description"
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
                    <Button
                      outlined
                      icon={<DeleteOutline />}
                      onClick={() => removeRow(index)}
                      type="button"
                    />
                  </Flex>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </SizeTable>

      <Flex>
        <Button
          type="submit"
          onClick={() => {}}
          title="Update"
          icon={<Save />}
        />
        <Button
          type="submit"
          onClick={() => {}}
          title="Delete"
          icon={<DeleteOutline />}
          outlined
        />
      </Flex>
    </Wrapper>
  );
};

export default SingleProduct;
