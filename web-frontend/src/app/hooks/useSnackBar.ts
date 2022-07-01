import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { closeSnackBarState, openSnackBar } from "../slices/snackbarSlice";
import { RootState } from "../store";

export const useSnackBar = (
  defaultMessage: string = "",
  dependency: any[] = []
) => {
  const dispatch = useDispatch();
  const { open, message, error } = useSelector(
    (state: RootState) => state.snackbar
  );

  const handleClose = () => {
    dispatch(closeSnackBarState());
  };

  const showSnackbar = (message: string, error: boolean = false) => {
    dispatch(openSnackBar({ open: true, message, error }));
  };

  return { open, message, showSnackbar, error, handleClose };
};
