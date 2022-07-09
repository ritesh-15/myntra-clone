import { FC, useEffect, useState } from "react";
import { useProducts } from "../../app/hooks/useProducts";
import { useSnackBar } from "../../app/hooks/useSnackBar";
import ProductApi from "../../api/products";
import {
  LoaderWrapper,
  Pagination,
  ProductsTopHeading,
  ProductsWrapper,
  ProductTopBarAction,
  ProductTopBarWrapper,
  Refetch,
  SearchOutlinedIcon,
  SearchWrapper,
  Wrapper,
} from "./Product.styled";
import {
  Skeleton,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import Paper from "@mui/material/Paper";
import { Link } from "react-router-dom";
import { Add, RefreshOutlined } from "@mui/icons-material";
import { Button, Loader } from "../../components";
import moment from "moment";
import { api } from "../../api/axios";
import ReactPaginate from "react-paginate";
import { PaginationResult } from "../../interfaces/PaginateResult";

const Products: FC = (): JSX.Element => {
  // hooks

  const { changeProductsState, products, isFetch, paginate, changePaginate } =
    useProducts();

  const { showSnackbar } = useSnackBar();

  // state
  const [search, setSearch] = useState<string>("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!search) return;

    const controller = new AbortController();
    (async () => {
      try {
        const { data } = await api.get(`/product/all?query=${search}`, {
          signal: controller.signal,
        });
        changeProductsState(data.products);
      } catch (error: any) {
        showSnackbar(error.response.data.message, true);
      }
    })();

    return () => {
      controller.abort();
    };
  }, [search]);

  const fetchProducts = async (page: number, limit: number) => {
    setLoading(true);
    try {
      const { data } = await ProductApi.getAllProducts(page, limit);
      changeProductsState(data.products);
      setLoading(false);
      changePaginate(data.result);
    } catch (error: any) {
      setLoading(false);
      showSnackbar(error.response.data.message, true);
    }
  };

  useEffect(() => {
    if (isFetch) return;
    fetchProducts(1, 5);
  }, [isFetch]);

  const handlePrevious = () => {
    if (!paginate) return;
    if (!paginate.previous) return;
    fetchProducts(paginate.previous.page, paginate.previous.limit);
  };

  const handleNext = () => {
    if (!paginate) return;
    if (!paginate.next) return;
    fetchProducts(paginate.next.page, paginate.next.limit);
  };

  return (
    <Wrapper>
      <Refetch onClick={() => fetchProducts(1, 5)}>
        <RefreshOutlined />
      </Refetch>
      <ProductTopBarWrapper>
        <ProductsTopHeading>
          <h5>Products Listed</h5>
        </ProductsTopHeading>
        <ProductTopBarAction>
          <SearchWrapper>
            <SearchOutlinedIcon />
            <input
              onChange={(e) => setSearch(e.target.value)}
              value={search}
              type="text"
              placeholder="Search"
            />
          </SearchWrapper>
          <Link to={"create"}>
            <Button title="Add Product" icon={<Add />} />
          </Link>
        </ProductTopBarAction>
      </ProductTopBarWrapper>

      <ProductsWrapper>
        {loading ? (
          <Loader />
        ) : (
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
        )}
      </ProductsWrapper>
      <Pagination>
        {paginate && paginate.previous && (
          <Button onClick={handlePrevious} title="Previous" outlined />
        )}

        {paginate && paginate.next && (
          <Button
            onClick={handleNext}
            style={{ marginLeft: "1em" }}
            title="Next"
            outlined
            disabled={paginate && !paginate.next ? true : false}
          />
        )}
      </Pagination>
    </Wrapper>
  );
};

export default Products;
