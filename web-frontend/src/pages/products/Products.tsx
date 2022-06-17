import { FC } from "react";
import {
  ProductsTopHeading,
  ProductsWrapper,
  ProductTopBarAction,
  ProductTopBarWrapper,
  SearchOutlinedIcon,
  SearchWrapper,
  Wrapper,
} from "./Product.styled";
import { DataGrid, GridColDef, GridValueGetterParams } from "@mui/x-data-grid";
import { Button, Table } from "../../components";
import { Add } from "@mui/icons-material";
import { Link, Outlet } from "react-router-dom";

const columns = [
  {
    field: "id",
    headerName: "ID",
  },
  {
    field: "productName",
    headerName: "Product Name",
  },
  {
    field: "category",
    headerName: "Category",
  },
  {
    field: "createdAt",
    headerName: "Created At",
  },
];

const rows = [
  {
    id: "1",
    productName: "Product 1",
    category: "Category 1",
    createdAt: "2020-01-01",
  },
  {
    id: "2",
    productName: "Product 1",
    category: "Category 1",
    createdAt: "2020-01-01",
  },
  {
    id: "3",
    productName: "Product 1",
    category: "Category 1",
    createdAt: "2020-01-01",
  },
  {
    id: "4",
    productName: "Product 1",
    category: "Category 1",
    createdAt: "2020-01-01",
  },
  {
    id: "4",
    productName: "Product 1",
    category: "Category 1",
    createdAt: "2020-01-01",
  },
  {
    id: "4",
    productName: "Product 1",
    category: "Category 1",
    createdAt: "2020-01-01",
  },
  {
    id: "4",
    productName: "Product 1",
    category: "Category 1",
    createdAt: "2020-01-01",
  },
  {
    id: "4",
    productName: "Product 1",
    category: "Category 1",
    createdAt: "2020-01-01",
  },
  {
    id: "4",
    productName: "Product 1",
    category: "Category 1",
    createdAt: "2020-01-01",
  },
  {
    id: "4",
    productName: "Product 1",
    category: "Category 1",
    createdAt: "2020-01-01",
  },
];

const Products: FC = (): JSX.Element => {
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

      <ProductsWrapper style={{ height: 400, width: "100%" }}>
        <Table columns={columns} rows={rows} />
      </ProductsWrapper>
    </Wrapper>
  );
};

export default Products;
