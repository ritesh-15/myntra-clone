import { FC } from "react";
import { ProductsWrapper, Wrapper } from "./Product.styled";
import { DataGrid, GridColDef, GridValueGetterParams } from "@mui/x-data-grid";
import { Button } from "../../components";
import { Add } from "@mui/icons-material";

const columns: GridColDef[] = [
  { field: "id", headerName: "ID", width: 70 },
  { field: "name", headerName: "Name", width: 130 },
  { field: "category", headerName: "Category", width: 130 },
  {
    field: "stock",
    headerName: "Stock",
    width: 160,
  },
  {
    field: "date create",
    headerName: "Date Create",
    width: 90,
  },
];

const rows = [
  { id: 1, lastName: "Snow", firstName: "Jon", age: 35 },
  { id: 2, lastName: "Lannister", firstName: "Cersei", age: 42 },
  { id: 3, lastName: "Lannister", firstName: "Jaime", age: 45 },
  { id: 4, lastName: "Stark", firstName: "Arya", age: 16 },
  { id: 5, lastName: "Targaryen", firstName: "Daenerys", age: null },
  { id: 6, lastName: "Melisandre", firstName: null, age: 150 },
  { id: 7, lastName: "Clifford", firstName: "Ferrara", age: 44 },
  { id: 8, lastName: "Frances", firstName: "Rossini", age: 36 },
  { id: 9, lastName: "Roxie", firstName: "Harvey", age: 65 },
];

const Products: FC = (): JSX.Element => {
  return (
    <Wrapper>
      <ProductsWrapper style={{ height: 400, width: "100%" }}>
        <DataGrid
          rows={rows}
          columns={columns}
          pageSize={5}
          rowsPerPageOptions={[10]}
          checkboxSelection
        />
      </ProductsWrapper>
      <br />
      <Button title="Add Product" icon={<Add />} />
    </Wrapper>
  );
};

export default Products;
