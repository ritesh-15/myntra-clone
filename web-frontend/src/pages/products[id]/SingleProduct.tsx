import { ChangeEvent, FC, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Button, Input, Loader, SelectBox, Textarea } from "../../components";
import {
  ProductCategory,
  ProductInterface,
} from "../../interfaces/product/ProductInterface";
import {
  AddImage,
  Col,
  Flex,
  ProductBasicInfo,
  ProductImage,
  ProductImages,
  Refetch,
  RemoveButton,
  Row,
  SizeTable,
  Wrapper,
} from "./SingleProduct.styled";
import ProductApi from "../../api/products";
import { useSnackBar } from "../../app/hooks/useSnackBar";
import {
  AddOutlined,
  DeleteOutline,
  Save,
  RefreshOutlined,
} from "@mui/icons-material";
import { useProducts } from "../../app/hooks/useProducts";

interface SizeInterface {
  title: string;
  description: string;
  measurement: string;
}

const SingleProduct: FC = (): JSX.Element => {
  // hooks
  const { id } = useParams();
  const { showSnackbar } = useSnackBar();
  const { categories, products, changeProductsState } = useProducts();
  const navigate = useNavigate();

  //states
  const [deleteLoading, setDeleteLoading] = useState(false);
  const [refetch, setRefetch] = useState(false);
  const [submitting, setSubmitting] = useState(false);
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
  const [category, changeCategory] = useState<string>("");

  useEffect(() => {
    (async () => {
      if (!id) return;

      setLoading(true);
      try {
        const { data } = await ProductApi.getSingleProduct(id, refetch);
        if (data.ok) {
          setProduct(data.product);
          setSizes(data.product.sizes);
          changeCategory(data.product.catagory.name);
        }
        showSnackbar("Product fetched successfully");
        setLoading(false);
      } catch (error: any) {
        console.log(error);
        showSnackbar(error.response.data.message, true);
        setLoading(false);
      }
    })();
  }, [id, refetch]);

  // handle product change
  const handleProductChange = (
    e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    if (!product) return;

    const { name, value } = e.target;

    setProduct({
      ...product,
      [name]: value,
    });
  };

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

  // handle update
  const handleUpdate = async () => {
    if (!product) return;

    const choosenCategory: ProductCategory | undefined = categories.find(
      (it) => it.name === category
    );

    setSubmitting(true);
    if (images.length > 0) {
      const formData = new FormData();
      images.forEach((image) => {
        formData.append("file", image);
      });

      try {
        await ProductApi.updateProductImage(product?.id, formData);
      } catch (error: any) {
        showSnackbar(error.response.data.message, true);
      }
    }

    try {
      const { data } = await ProductApi.updateProduct(product.id, {
        name: product.name,
        description: product.description,
        fabric: product.fabric,
        fit: product.fit,
        stock: product.stock,
        discount: product.discount,
        originalPrice: product.originalPrice,
        categoryId: choosenCategory?.id,
      });

      setProduct(data.product);
      setSubmitting(false);
      showSnackbar("Product Updated Successfully!");
    } catch (error: any) {
      showSnackbar(error.response.data.message, true);
      setSubmitting(false);
    }
  };

  // delete product
  const deleteProduct = async () => {
    if (!product) return;

    setDeleteLoading(true);

    try {
      await ProductApi.deleteProduct(product.id);
      showSnackbar("Product deleted successfully");
      changeProductsState(products.filter((it) => it.id !== product.id));
      setDeleteLoading(false);
      navigate("/products");
    } catch (error: any) {
      navigate("/products");
      showSnackbar(error.response.data.message, true);
      setDeleteLoading(false);
    }
  };

  // delete image
  const deleteImage = async (id: string) => {
    if (!product) return;
    setLoading(true);
    try {
      await ProductApi.deleteProductImage(id);
      showSnackbar("Images removed successfully!");
      setProduct({
        ...product,
        images: product.images.filter((image) => image.publicId !== id),
      });
      setLoading(false);
    } catch (error: any) {
      setLoading(false);
      showSnackbar(error.response.data.message, true);
    }
  };

  if (loading)
    return (
      <Wrapper
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        <Loader style={{ height: "100%" }} />
      </Wrapper>
    );

  return (
    <Wrapper>
      <Refetch onClick={() => setRefetch(true)}>
        <RefreshOutlined />
      </Refetch>
      <ProductImages>
        {product?.images.map((image) => {
          return (
            <ProductImage key={image.publicId}>
              <img src={image.url} alt="" />
              <RemoveButton onClick={() => deleteImage(image.publicId)}>
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
            <Input
              onChange={handleProductChange}
              name="name"
              value={product?.name}
              title="Product Name"
            />
          </Col>
          <Col>
            <h6>Product Category</h6>
            <SelectBox
              options={categories.map((category) => category.name)}
              current={category}
              changeCurrent={(value) => changeCategory(value)}
              label="Choose Category"
            />
          </Col>

          <Col>
            <h6>Price</h6>
            <Input
              name="originalPrice"
              value={product?.originalPrice}
              title="Price"
              onChange={handleProductChange}
            />
          </Col>

          <Col>
            <h6>Discount</h6>
            <Input
              onChange={handleProductChange}
              name="discount"
              value={product?.discount}
              title="Discount"
            />
          </Col>

          <Col>
            <h6>Discount Price</h6>
            <p>{product?.discountPrice}</p>
          </Col>

          <Col>
            <h6>Fabric</h6>
            <Input
              onChange={handleProductChange}
              name="fabric"
              value={product?.fabric}
              title="Fabric"
            />
          </Col>

          <Col>
            <h6>Fit</h6>
            <Input
              onChange={handleProductChange}
              name="fit"
              value={product?.fit}
              title="Fit"
            />
          </Col>
          <Col>
            <h6>Stocks</h6>
            <Input
              onChange={handleProductChange}
              name="stock"
              value={product?.stock}
              title="Stocks"
            />
          </Col>
          <Col>
            <h6>Product Description</h6>
            <Textarea
              value={product?.description}
              title="Product Description"
              name="description"
              onChange={handleProductChange}
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
          onClick={handleUpdate}
          title="Update"
          loading={submitting}
          icon={<Save />}
        />
        <Button
          type="submit"
          onClick={deleteProduct}
          title="Delete"
          icon={<DeleteOutline />}
          outlined
          loading={deleteLoading}
        />
      </Flex>
    </Wrapper>
  );
};

export default SingleProduct;
