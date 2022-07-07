import { RefreshOutlined, SearchOutlined } from "@mui/icons-material";
import { FC, useEffect, useState } from "react";
import UserApi from "../../api/user";
import { useSnackBar } from "../../app/hooks/useSnackBar";
import { useUser } from "../../app/hooks/useUser";
import {
  Active,
  LoaderWrapper,
  ProductsTopHeading,
  ProductTopBarAction,
  ProductTopBarWrapper,
  Refetch,
  SearchWrapper,
  Wrapper,
} from "./User.styled";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
} from "@mui/material";
import { Link } from "react-router-dom";
import moment from "moment";
import { Loader } from "../../components";

const Users: FC = (): JSX.Element => {
  // hooks
  const { showSnackbar } = useSnackBar();
  const { changeUsersState, isFetch, users } = useUser();

  // state
  const [search, setSearch] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(false);

  // fetch users
  const fetchUsers = async () => {
    setLoading(true);
    try {
      const { data } = await UserApi.getAllUsers();
      if (data.ok) {
        changeUsersState(data.users);
      }
      setLoading(false);
    } catch (error: any) {
      showSnackbar(error.response.data.message, true);
      setLoading(false);
    }
  };

  useEffect(() => {
    if (isFetch) {
      return;
    }

    fetchUsers();
  }, [isFetch]);

  if (loading)
    return (
      <LoaderWrapper>
        <Loader />
      </LoaderWrapper>
    );

  return (
    <Wrapper>
      <Refetch onClick={() => fetchUsers()}>
        <RefreshOutlined />
      </Refetch>
      <ProductTopBarWrapper>
        <ProductsTopHeading>
          <h5>Users</h5>
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
        </ProductTopBarAction>
      </ProductTopBarWrapper>

      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell align="right">User Name</TableCell>
              <TableCell align="right">User Email</TableCell>
              <TableCell align="right">User Phone</TableCell>
              <TableCell align="right">Is Active</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {users.map((row) => (
              <TableRow
                key={row?.id}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell component="th" scope="row">
                  <Link to={`/users/${row?.id}`}>{row?.id}</Link>
                </TableCell>
                <TableCell align="right">{row?.name}</TableCell>
                <TableCell align="right">{row?.email}</TableCell>
                <TableCell align="right">{row?.phoneNumber}</TableCell>
                <TableCell align="right">
                  <Active active={row.isActive} />
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Wrapper>
  );
};

export default Users;
