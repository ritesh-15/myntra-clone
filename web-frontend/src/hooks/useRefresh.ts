import { useEffect, useState } from "react";
import UserApi from "../api/user";
import { useSnackBar } from "../app/hooks/useSnackBar";
import { useUser } from "../app/hooks/useUser";

export const useRefresh = () => {
  const { setUserData } = useUser();
  const { showSnackbar } = useSnackBar();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    (async () => {
      try {
        const { data } = await UserApi.refresh();
        if (data.ok) {
          setUserData(data.user);
        }
        setLoading(false);
      } catch (error: any) {
        setLoading(false);
        showSnackbar(error.response.data.message);
      }
    })();
  }, []);

  return loading;
};
