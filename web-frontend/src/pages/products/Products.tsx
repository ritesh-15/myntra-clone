import { FC, useEffect, useState } from "react";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { useProducts } from "../../app/hooks/useProducts";
import { useSnackBar } from "../../app/hooks/useSnackBar";
import ProductApi from "../../api/products";
import { Wrapper } from "./Product.styled";

const columns: GridColDef[] = [
  { field: "id", headerName: "ID" },
  { field: "name", headerName: "Product Name" },
  { field: "description", headerName: "Product Description" },
  {
    field: "createdAt",
    headerName: "CreatedAt",
    minWidth: 150,
  },
];

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
      <DataGrid
        rows={products}
        columns={columns}
        pageSize={8}
        rowsPerPageOptions={[8]}
        checkboxSelection
      />
    </Wrapper>
  );
};

export default Products;
