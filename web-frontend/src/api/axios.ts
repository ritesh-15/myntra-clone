import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:9000/api/v1",
  withCredentials: true,
  headers: {
    "content-type": "application/json",
  },
});
