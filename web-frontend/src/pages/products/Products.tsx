import { FC, useEffect, useState } from "react";
import { useProducts } from "../../app/hooks/useProducts";
import { useSnackBar } from "../../app/hooks/useSnackBar";
import ProductApi from "../../api/products";
import {
  ProductsTopHeading,
  ProductsWrapper,
  ProductTopBarAction,
  ProductTopBarWrapper,
  SearchOutlinedIcon,
  SearchWrapper,
  Wrapper,
} from "./Product.styled";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import Paper from "@mui/material/Paper";
import { Link } from "react-router-dom";
import { Add } from "@mui/icons-material";
import { Button } from "../../components";
import moment from "moment";

const Products: FC = (): JSX.Element => {
  // hooks

  const { changeProductsState, products, isFetch } = useProducts();

  const { showSnackbar } = useSnackBar();

  // state

  useEffect(() => {
    if (isFetch) return;

    (async () => {
      try {
        const { data } = await ProductApi.getAllProducts();
        changeProductsState(data.products);
      } catch (error: any) {
        showSnackbar(error.response.data.message, true);
      }
    })();
  }, [isFetch]);

  return (
    <Wrapper>
      <ProductTopBarWrapper>
        <ProductsTopHeading>
          <h5>Products Listed</h5>
        </ProductsTopHeading>
        <ProductTopBarAction>
          <SearchWrapper>
            <SearchOutlinedIcon />
            <input type="text" placeholder="Search" />
          </SearchWrapper>
          <Link to={"create"}>
            <Button title="Add Product" icon={<Add />} />
          </Link>
        </ProductTopBarAction>
      </ProductTopBarWrapper>

      <ProductsWrapper>
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>ID</TableCell>
                <TableCell align="right">Product Name</TableCell>
                <TableCell align="right">Product Category</TableCell>
                <TableCell align="right">Created At</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {products.map((row) => (
                <TableRow
                  key={row?.id}
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell component="th" scope="row">
                    <Link to={`/products/${row?.id}`}>{row?.id}</Link>
                  </TableCell>
                  <TableCell align="right">{row?.name}</TableCell>
                  <TableCell align="right">{row?.catagory?.name}</TableCell>
                  <TableCell align="right">
                    {moment(row?.createdAt).format("hh:mm A, DD MMMM YY")}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </ProductsWrapper>
    </Wrapper>
  );
};

export default Products;
