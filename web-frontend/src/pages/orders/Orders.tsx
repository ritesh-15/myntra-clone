import { Add, RefreshOutlined, SearchOutlined } from "@mui/icons-material";
import { FC, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Button, Loader } from "../../components";
import {
  OrdersTopHeading,
  OrderTopBarAction,
  OrderTopBarWrapper,
  SearchWrapper,
  Wrapper,
  OrdersWrapper,
  Refetch,
  LoaderWrapper,
  Pagination,
} from "./Orders.styled";
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
import { useSnackBar } from "../../app/hooks/useSnackBar";
import { useOrders } from "../../app/hooks/useOrders";
import OrderApi from "../../api/OrderApi";
import { PaginationResult } from "../../interfaces/PaginateResult";

const Orders: FC = (): JSX.Element => {
  // hooks
  const { showSnackbar } = useSnackBar();
  const { changeOrdersState, orders, isFetch } = useOrders();

  // state
  const [search, setSearch] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(false);
  const [paginate, setPaginate] = useState<PaginationResult | null>(null);

  // get orders
  const fetchOrders = async (
    page: number = 1,
    limit: number = 5,
    search: string = "",
    controller?: AbortController
  ) => {
    setLoading(true);
    try {
      const { data } = await OrderApi.getAllOrders(
        page,
        limit,
        search,
        controller?.signal
      );
      if (data.ok) {
        changeOrdersState(data.orders);
        setPaginate(data.result);
      }
      showSnackbar("Orders fetched successfully!");
      setLoading(false);
    } catch (error: any) {
      setLoading(false);
      showSnackbar(error.response.data.message, true);
    }
  };

  useEffect(() => {
    if (isFetch) return;
    fetchOrders();
  }, [isFetch]);

  useEffect(() => {
    if (search === "") return;

    const controller = new AbortController();

    fetchOrders(1, 5, search, controller);

    return () => {
      controller.abort();
    };
  }, [search]);

  const handlePrevious = () => {
    if (!paginate) return;
    if (!paginate.previous) return;
    fetchOrders(paginate.previous.page, paginate.previous.limit);
  };

  const handleNext = () => {
    if (!paginate) return;
    if (!paginate.next) return;
    fetchOrders(paginate.next.page, paginate.next.limit);
  };

  return (
    <Wrapper>
      <Refetch onClick={() => fetchOrders()}>
        <RefreshOutlined />
      </Refetch>
      <OrderTopBarWrapper>
        <OrdersTopHeading>
          <h5>Orders Listed</h5>
        </OrdersTopHeading>
        <OrderTopBarAction>
          <SearchWrapper>
            <SearchOutlined />
            <input
              onChange={(e) => setSearch(e.target.value)}
              value={search}
              type="text"
              placeholder="Search"
            />
          </SearchWrapper>
        </OrderTopBarAction>
      </OrderTopBarWrapper>

      <OrdersWrapper>
        {loading ? (
          <Loader />
        ) : (
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>ID</TableCell>
                  <TableCell align="right">User Name</TableCell>
                  <TableCell align="right">Payment Status</TableCell>
                  <TableCell align="right">Order Status</TableCell>
                  <TableCell align="right">Created At</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {orders.map((row) => (
                  <TableRow
                    key={row?.id}
                    sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                  >
                    <TableCell component="th" scope="row">
                      <Link to={`/orders/${row?.id}`}>{row?.id}</Link>
                    </TableCell>
                    <TableCell align="right">{row?.user?.name}</TableCell>
                    <TableCell align="right">
                      {row?.payment?.map((payment) => {
                        if (payment.paymentStatus) {
                          return "Paid";
                        } else {
                          return "Not Paid";
                        }
                      })}
                    </TableCell>
                    <TableCell align="right">
                      {row?.extra?.orderStatus}
                    </TableCell>
                    <TableCell align="right">
                      {moment(row?.createdAt).format("hh:mm A, DD MMMM YY")}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        )}
      </OrdersWrapper>
      <Pagination>
        <Button
          disabled={paginate && !paginate.previous ? true : false}
          onClick={handlePrevious}
          title="Previous"
          outlined
        />

        <Button
          onClick={handleNext}
          style={{ marginLeft: "1em" }}
          title="Next"
          outlined
          disabled={paginate && !paginate.next ? true : false}
        />
      </Pagination>
    </Wrapper>
  );
};

export default Orders;
