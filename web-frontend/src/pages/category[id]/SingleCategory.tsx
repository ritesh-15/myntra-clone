import React, { FC, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import ProductApi from "../../api/products";
import { useSnackBar } from "../../app/hooks/useSnackBar";
import {
  ProductCategory,
  ProductInterface,
} from "../../interfaces/product/ProductInterface";
import {
  Heading,
  Refetch,
  Section,
  SectionHeading,
  Wrapper,
} from "./SingleCategory.styled";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Skeleton,
} from "@mui/material";
import moment from "moment";
import { RefreshOutlined } from "@mui/icons-material";

interface SingleCategoryInterface extends ProductCategory {
  Product: ProductInterface[];
}

const SingleCategory: FC = (): JSX.Element => {
  // hooks
  const { showSnackbar } = useSnackBar();
  const { id } = useParams();
  const navigate = useNavigate();

  // state
  const [category, setCategory] = useState<SingleCategoryInterface | null>(
    null
  );

  const fetchCategory = async (refetch: boolean = false) => {
    if (!id) return;

    try {
      const { data } = await ProductApi.getSingleCategory(id, refetch);
      setCategory(data.category);
    } catch (error: any) {
      showSnackbar(error.response.data.message, true);
      navigate("/categories");
    }
  };

  useEffect(() => {
    fetchCategory();
  }, [id]);

  return (
    <Wrapper>
      <Heading>{category?.name}</Heading>
      <Refetch onClick={() => fetchCategory(true)}>
        <RefreshOutlined />
      </Refetch>
      <Section>
        <SectionHeading>Products</SectionHeading>
        {category && category.Product.length > 0 ? (
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 700 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>ID</TableCell>
                  <TableCell align="right">Payment Name</TableCell>
                  <TableCell align="right">Product Description</TableCell>
                  <TableCell align="right">Stocks</TableCell>
                  <TableCell align="right">Created At</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {category?.Product &&
                  category?.Product.map((row) => (
                    <TableRow
                      key={row?.id}
                      sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                    >
                      <TableCell component="th" scope="row">
                        <Link to={`/products/${row?.id}`}>{row?.id}</Link>
                      </TableCell>
                      <TableCell align="right">{row?.name}</TableCell>
                      <TableCell align="right">{row?.description}</TableCell>
                      <TableCell align="right">{row?.stock}</TableCell>
                      <TableCell align="right">
                        {moment(row?.createdAt).format("hh:mm A, DD MMMM YY")}
                      </TableCell>
                    </TableRow>
                  ))}
              </TableBody>
            </Table>
          </TableContainer>
        ) : (
          <p>No products found!</p>
        )}
      </Section>
    </Wrapper>
  );
};

export default SingleCategory;
