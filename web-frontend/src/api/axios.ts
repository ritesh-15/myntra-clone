import axios from "axios";

export const api = axios.create({
  baseURL: "https://myntra-clone15.herokuapp.com/api/v1",
  withCredentials: true,
  headers: {
    "content-type": "application/json",
  },
});
