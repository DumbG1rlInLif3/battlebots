
// authApi.jsx
import axios from "axios";

const authApi = axios.create({
  baseURL: "http://localhost:8080", // sem /api
  withCredentials: true,
});

export default authApi;