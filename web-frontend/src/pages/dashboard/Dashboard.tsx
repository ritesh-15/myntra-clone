import {
  ShoppingCartOutlined,
  PaidOutlined,
  PersonOutline,
} from "@mui/icons-material";
import { FC } from "react";
import { DashboardCard } from "../../components";
import {
  DashboardCardContainer,
  RevenueChart,
  Wrapper,
  Title as Heading,
  BottomRow,
  LatestTransactions,
  NewJoinedUsers,
} from "./Dashboard.styled";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  Filler,
} from "chart.js";
import { Line } from "react-chartjs-2";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  Filler
);

export const options = {
  responsive: true,
  plugins: {
    legend: {
      display: false,
    },
  },
  tension: 0.4,
};

const labels = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

const data = {
  labels,
  datasets: [
    {
      label: "Earning",
      data: [77, 15, 45, 33, 66.66, 87, 41, 70, 33, 81, 69, 52],
      borderColor: "hsl(346, 100%, 68%)",
      backgroundColor: "hsl(346, 100%, 95%)",
      borderRadius: 1,
      fill: true,
    },
  ],
};

const Dashboard: FC = (): JSX.Element => {
  return (
    <Wrapper>
      <DashboardCardContainer>
        <DashboardCard
          options={{
            title: "users",
            link: "See all users",
            path: "/users",
            count: 412,
            icon: <PersonOutline />,
            percentage: 30,
            background: "hsl(346, 100%, 95%)",
            color: "hsl(346, 100%, 62%)",
            up: true,
          }}
        />

        <DashboardCard
          options={{
            title: "orders",
            link: "See all orders",
            path: "/orders",
            count: 125,
            icon: <ShoppingCartOutlined />,
            percentage: 70,
            background: "hsl(49, 100%, 95%)",
            color: "hsl(49, 100%, 62%)",
          }}
        />

        <DashboardCard
          options={{
            title: "Earnings",
            link: "See all earning",
            path: "/earning",
            count: 78,
            icon: <PaidOutlined />,
            percentage: 20,
            background: "hsl(136, 64%, 95%)",
            color: "hsl(136, 64%, 65%)",
            up: true,
          }}
        />
      </DashboardCardContainer>
      {/*  Revenue graph */}

      <br />

      <Heading>Revenue</Heading>

      <RevenueChart>
        <Line options={options} data={data} />
      </RevenueChart>

      <br />

      <BottomRow>
        <LatestTransactions>
          <Heading>Latest Transactions</Heading>
          <br />
          <TableContainer component={Paper}>
            <Table sx={{ overflow: "auto" }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>Transition Id</TableCell>
                  <TableCell align="right">Transition Date</TableCell>
                  <TableCell align="right">Amount</TableCell>
                  <TableCell align="right">Transition Status</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                <TableRow>
                  <TableCell align="left">44578</TableCell>
                  <TableCell align="right">22 Jan 2022</TableCell>
                  <TableCell align="right">400</TableCell>
                  <TableCell align="right">Success</TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </TableContainer>
        </LatestTransactions>
        <NewJoinedUsers>
          <Heading>Joined Users</Heading>
          <br />
          <TableContainer component={Paper}>
            <Table sx={{ overflow: "auto" }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>User ID</TableCell>
                  <TableCell align="right">User Name</TableCell>
                  <TableCell align="right">Is Active</TableCell>
                  <TableCell align="right">Joined Date</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                <TableRow>
                  <TableCell align="left">445</TableCell>
                  <TableCell align="right">Ritesh Khore</TableCell>
                  <TableCell align="right">Active</TableCell>
                  <TableCell align="right">16 Jun 2022</TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </TableContainer>
        </NewJoinedUsers>
      </BottomRow>
    </Wrapper>
  );
};

export default Dashboard;
