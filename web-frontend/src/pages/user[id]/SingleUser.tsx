import { FC, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import UserApi from "../../api/user";
import { useSnackBar } from "../../app/hooks/useSnackBar";
import { Button, SelectBox } from "../../components";
import { Address, OrderInterface } from "../../interfaces/OrderInterface";
import { UserInterface } from "../../interfaces/UserInterface";
import {
  Col,
  Row,
  Section,
  SectionHeading,
  SelectWrapper,
  Wrapper,
} from "./SingleUser.styled";
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

interface SingUserInterface extends UserInterface {
  address: Address[];
  orders: OrderInterface[];
}

const OPTIONS = ["USER", "ADMIN"];

const SingleUser: FC = (): JSX.Element => {
  // hooks
  const { id } = useParams();
  const { showSnackbar } = useSnackBar();

  // state
  const [loading, setLoading] = useState<boolean>(true);
  const [user, setUser] = useState<SingUserInterface | null>(null);
  const [type, setType] = useState<string>("USER");
  const [buttonLoading, setButtonLoading] = useState<boolean>(false);

  const handleClick = async () => {
    if (!user) return;
    setButtonLoading(true);
    try {
      const { data } = await UserApi.makeAdmin(user.id, {
        isAdmin: type === "ADMIN",
      });
      showSnackbar("User updated successfully");
      setButtonLoading(false);
    } catch (error: any) {
      showSnackbar("Error updating user", true);
      setButtonLoading(false);
    }
  };

  const fetchUser = async () => {
    if (!id) return;

    try {
      const { data } = await UserApi.getSingleUser(id);
      if (data.ok) {
        setUser(data.user);
        console.log(data.user);
        if (data.user.isAdmin) {
          setType("ADMIN");
        }
      }
      setLoading(false);
    } catch (error: any) {
      showSnackbar(error.response.data.message, true);
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUser();
  }, [id]);

  if (loading)
    return (
      <Wrapper>
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
      </Wrapper>
    );

  return (
    <Wrapper>
      <Section>
        <SectionHeading>User</SectionHeading>
        <Row>
          <Col>
            <label>User ID</label>
            <p>{user?.id}</p>
          </Col>
          <Col>
            <label>Name</label>
            <p>{user?.name}</p>
          </Col>
          <Col>
            <label>Email</label>
            <p>{user?.email}</p>
          </Col>
          <Col>
            <label>Phone</label>
            <p>{user?.phoneNumber}</p>
          </Col>
          <Col>
            <label>Is Active</label>
            <p>{user?.isActive ? "Active" : "In Active"}</p>
          </Col>
          <Col>
            <label>User Type</label>
            <SelectWrapper>
              <SelectBox
                options={OPTIONS}
                current={type}
                changeCurrent={(value) => setType(value)}
                label="User Type"
              />
              <Button
                fullWidth
                onClick={handleClick}
                title="Update"
                loading={buttonLoading}
                style={{ marginTop: "1em" }}
              />
            </SelectWrapper>
          </Col>
        </Row>
      </Section>
      <Section>
        <SectionHeading>Address</SectionHeading>
        {user?.address && user.address.length > 0 ? (
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 700 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>ID</TableCell>
                  <TableCell align="right">Address</TableCell>
                  <TableCell align="right">Country</TableCell>
                  <TableCell align="right">State</TableCell>
                  <TableCell align="right">City</TableCell>
                  <TableCell align="right">Pin Code</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {user?.address?.map((row) => (
                  <TableRow
                    key={row?.id}
                    sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                  >
                    <TableCell component="th" scope="row">
                      {row?.id}
                    </TableCell>
                    <TableCell align="right">{row?.address}</TableCell>
                    <TableCell align="right">{row?.country}</TableCell>
                    <TableCell align="right">{row?.state}</TableCell>
                    <TableCell align="right">{row?.city}</TableCell>
                    <TableCell align="right">{row?.pincode}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        ) : (
          <p>No address found</p>
        )}
      </Section>

      <Section>
        <SectionHeading>Orders History</SectionHeading>
        {user?.orders && user?.orders.length > 0 ? (
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 700 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>ID</TableCell>
                  <TableCell align="right">Address Id</TableCell>
                  <TableCell align="right">Ordered Date</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {user?.orders?.map((row) => (
                  <TableRow
                    key={row?.id}
                    sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                  >
                    <TableCell component="th" scope="row">
                      <Link to={`/orders/${row?.id}`}>{row?.id}</Link>
                    </TableCell>
                    <TableCell align="right">{row?.addressId}</TableCell>
                    <TableCell align="right">
                      {moment(row?.createdAt).format("hh:mm A, DD MMMM YY")}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        ) : (
          <p>No orders found</p>
        )}
      </Section>
    </Wrapper>
  );
};

export default SingleUser;
