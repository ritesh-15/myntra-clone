import {
  Add,
  DeleteOutline,
  RefreshOutlined,
  SearchOutlined,
} from "@mui/icons-material";
import { FC, useCallback, useState } from "react";
import { Link } from "react-router-dom";
import ProductApi from "../../api/products";
import { useProducts } from "../../app/hooks/useProducts";
import { useSnackBar } from "../../app/hooks/useSnackBar";
import { Button } from "../../components";
import {
  LoaderWrapper,
  ProductsTopHeading,
  ProductTopBarAction,
  ProductTopBarWrapper,
  Refetch,
  SearchWrapper,
  Wrapper,
} from "./Categories.styled";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
} from "@mui/material";
import moment from "moment";
import CreateCategory from "../../components/create-category/CreateCategory";

const Categories: FC = (): JSX.Element => {
  // hooks
  const { categories, changeCategoriesState } = useProducts();

  const { showSnackbar } = useSnackBar();

  // state
  const [search, setSearch] = useState<string>("");
  const [open, setOpen] = useState<boolean>(false);

  const fetchCategories = useCallback(async () => {
    try {
      const { data } = await ProductApi.getAllCategories();
      changeCategoriesState(data.categories);
    } catch (error: any) {
      showSnackbar(error.response.data.message, true);
    }
  }, []);

  return (
    <Wrapper>
      {open && <CreateCategory setOpen={setOpen} />}
      <Refetch onClick={fetchCategories}>
        <RefreshOutlined />
      </Refetch>
      <ProductTopBarWrapper>
        <ProductsTopHeading>
          <h5>Categories</h5>
        </ProductsTopHeading>
        <ProductTopBarAction>
          <SearchWrapper>
            <SearchOutlined />
            <input
              onChange={(e) => setSearch(e.target.value)}
              value={search}
              type="text"
              placeholder="Search"
            />
          </SearchWrapper>
          <Button
            onClick={() => setOpen(true)}
            title="Create Category"
            icon={<Add />}
          />
        </ProductTopBarAction>
      </ProductTopBarWrapper>
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell align="right">Category Name</TableCell>
              <TableCell align="right">Created At</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {categories.map((row) => (
              <TableRow
                key={row?.id}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell component="th" scope="row">
                  <Link to={`/categories/${row?.id}`}>{row?.id}</Link>
                </TableCell>
                <TableCell align="right">{row?.name}</TableCell>
                <TableCell align="right">
                  {moment(row?.createdAt).format("hh:mm A, DD MMMM YY")}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Wrapper>
  );
};

export default Categories;
