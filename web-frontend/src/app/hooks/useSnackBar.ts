import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { openSnackBar } from "../slices/snackbarSlice";
import { RootState } from "../store";

export const useSnackBar = (
  defaultMessage: string = "",
  dependency: any[] = []
) => {
  const dispatch = useDispatch();
  const { open, message } = useSelector((state: RootState) => state.snackbar);

  const showSnackbar = (message: string) => {
    dispatch(openSnackBar({ open: true, message }));
  };

  useEffect(() => {
    showSnackbar(defaultMessage);
  }, dependency);

  return { open, message, showSnackbar };
};
