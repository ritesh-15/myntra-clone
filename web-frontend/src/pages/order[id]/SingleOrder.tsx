import React, { FC, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import OrderApi from "../../api/OrderApi";
import { useSnackBar } from "../../app/hooks/useSnackBar";
import { OrderInterface } from "../../interfaces/OrderInterface";
import {
  Heading,
  Row,
  Section,
  SectionHeading,
  Wrapper,
  Col,
  OrderStatus,
  SelectWrapper,
  Flex,
} from "./SingleOrder.styled";
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
import { Button, SelectBox } from "../../components";

const ORDER_STATUS = {
  PENDING: "PENDING",
  CONFIRMED: "CONFIRMED",
  CANCELLED: "CANCELLED",
  DELIVERED: "DELIVERED",
  OUT: "OUT FOR DELIVERY",
};

const OPTIONS = [
  "PENDING",
  "CONFIRMED",
  "OUT FOR DELIVERY",
  "CANCELLED",
  "DELIVERED",
];

const SingleOrder: FC = (): JSX.Element => {
  // hooks
  const { showSnackbar } = useSnackBar();
  const { id } = useParams();
  const navigate = useNavigate();

  // state
  const [order, setOrder] = useState<OrderInterface | null>(null);
  const [status, setStatus] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(true);
  const [updating, setUpdating] = useState<boolean>(false);

  // get order
  const fetchOrder = async (id: string) => {
    try {
      const { data } = await OrderApi.getSingleOrder(id);
      if (data.ok) {
        setOrder(data.order);
        setStatus(data.order.extra.orderStatus);
      }
      setLoading(false);
      showSnackbar("Order fetched successfully!");
    } catch (error: any) {
      setLoading(false);
      showSnackbar(error.response.data.message, true);
      navigate("/orders");
    }
  };

  // handle process click
  const handleClick = async () => {
    if (!order) return;
    setUpdating(true);
    try {
      const { data } = await OrderApi.processOrder(order?.id, {
        orderStatus: status,
      });
      if (data.ok) {
        showSnackbar("Order processed successfully!");
        setOrder(data.order);
      }
      setUpdating(false);
    } catch (error: any) {
      showSnackbar(error.response.data.message, true);
      setUpdating(false);
    }
  };

  useEffect(() => {
    if (!id) return;
    fetchOrder(id);
  }, [id]);

  if (loading)
    return (
      <Wrapper>
        <Skeleton variant="text" width={300} height={50} />
        <br />
        <Row>
          <Col>
            <Skeleton variant="text" />
            <Skeleton variant="text" height={100} />
          </Col>
          <Col>
            <Skeleton variant="text" />
            <Skeleton variant="text" height={100} />
          </Col>
          <Col>
            <Skeleton variant="text" />
            <Skeleton variant="text" height={100} />
          </Col>
          <Col>
            <Skeleton variant="text" />
            <Skeleton variant="text" height={100} />
          </Col>
          <Col>
            <Skeleton variant="text" />
            <Skeleton variant="text" height={100} />
          </Col>
        </Row>
        <br />
        <Row>
          <Col>
            <Skeleton variant="text" />
            <Skeleton variant="text" height={100} />
          </Col>
          <Col>
            <Skeleton variant="text" />
            <Skeleton variant="text" height={100} />
          </Col>
          <Col>
            <Skeleton variant="text" />
            <Skeleton variant="text" height={100} />
          </Col>
        </Row>
      </Wrapper>
    );

  return (
    <Wrapper>
      <Flex>
        <Heading>{order?.id}</Heading>
        <OrderStatus>{order?.extra.orderStatus}</OrderStatus>
      </Flex>
      <Section>
        <SectionHeading>User Details</SectionHeading>
        <Row>
          <Col>
            <label>User Id</label>
            <p>{order?.user.id}</p>
          </Col>
          <Col>
            <label>Name</label>
            <p>{order?.user.name}</p>
          </Col>
          <Col>
            <label>Email</label>
            <p>{order?.user.email}</p>
          </Col>
          <Col>
            <label>Phone Number</label>
            <p>{order?.user.phoneNumber}</p>
          </Col>
        </Row>
      </Section>
      <Section>
        <SectionHeading>Address Details</SectionHeading>
        <Row>
          <Col>
            <label>Address</label>
            <p>{order?.address.address}</p>
          </Col>
          <Col>
            <label>Country</label>
            <p>{order?.address.country}</p>
          </Col>
          <Col>
            <label>State</label>
            <p>{order?.address.state}</p>
          </Col>
          <Col>
            <label>City</label>
            <p>{order?.address.city}</p>
          </Col>
          <Col>
            <label>Pin Code</label>
            <p>{order?.address?.pincode?.toString()}</p>
          </Col>
          <Col>
            <label>Landmark</label>
            <p>
              {order?.address.nearestLandmark
                ? order?.address.nearestLandmark
                : "NA"}
            </p>
          </Col>
          <Col>
            <label>Phone Number</label>
            <p>
              {order?.address.phoneNumber ? order?.address.phoneNumber : "NA"}
            </p>
          </Col>
        </Row>
      </Section>

      <Section>
        <SectionHeading>Payment History</SectionHeading>
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 700 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>ID</TableCell>
                <TableCell align="right">Payment Type</TableCell>
                <TableCell align="right">Total Amount</TableCell>
                <TableCell align="right">Status</TableCell>
                <TableCell align="right">Paid At</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {order?.payment.map((row) => (
                <TableRow
                  key={row?.id}
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell component="th" scope="row">
                    <Link to={`/products/${row?.id}`}>{row?.id}</Link>
                  </TableCell>
                  <TableCell align="right">{row?.paymentType}</TableCell>
                  <TableCell align="right">{row?.total}</TableCell>
                  <TableCell align="right">
                    {row?.paymentStatus ? "Paid" : "Not Paid"}
                  </TableCell>
                  <TableCell align="right">
                    {moment(row?.createdAt).format("hh:mm A, DD MMMM YY")}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Section>

      <Section>
        <SectionHeading>Order Items</SectionHeading>
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>ID</TableCell>
                <TableCell align="right">Product Name</TableCell>
                <TableCell align="right">Product Price</TableCell>
                <TableCell align="right">Product Size</TableCell>
                <TableCell align="right">Stocks</TableCell>
                <TableCell align="right">Quantity</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {order?.products.map((row) => (
                <TableRow
                  key={row?.id}
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell component="th" scope="row">
                    <Link to={`/products/${row?.product.id}`}>
                      {row?.product.id}
                    </Link>
                  </TableCell>
                  <TableCell align="right">{row?.product.name}</TableCell>
                  <TableCell align="right">
                    {row?.product.originalPrice}
                  </TableCell>
                  <TableCell align="right">{row?.size.title}</TableCell>
                  <TableCell align="right">{row?.product.stock}</TableCell>
                  <TableCell align="right">{row?.quantity}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Section>

      <Section>
        <SectionHeading>Order Details</SectionHeading>
        <Row>
          <Col>
            <label>Order Id</label>
            <p>{order?.id}</p>
          </Col>
          <Col>
            <label>Delivery Cost</label>
            <p>{order?.extra.deliveryCost}</p>
          </Col>
          <Col>
            <label>Delivery Date</label>
            <p>{moment(order?.extra.updatedAt).format("DD MMMM YY")}</p>
          </Col>
          <Col>
            <label>Last Changed</label>
            <p>
              {moment(order?.extra.updatedAt).format("hh:mm A, DD MMMM YY")}
            </p>
          </Col>
          <Col>
            <label>Order Status</label>
            <SelectWrapper>
              <SelectBox
                options={OPTIONS}
                current={status}
                changeCurrent={(value) => setStatus(value)}
                label="Change Status"
              />
              <Button
                fullWidth
                onClick={handleClick}
                loading={updating}
                title="Process"
                style={{ marginTop: "1em" }}
              />
            </SelectWrapper>
          </Col>
        </Row>
      </Section>
    </Wrapper>
  );
};

export default SingleOrder;
