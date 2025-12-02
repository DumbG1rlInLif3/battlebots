import axios from "axios";
import { getToken } from "../services/authService"; // pega token do localStorage

const api = axios.create({
  baseURL: "http://localhost:8080/api",
  withCredentials: true, // se o backend usa cookies, mantém
});

// Interceptor adiciona token em todas as requisições
api.interceptors.request.use((config) => {
  const token = getToken();
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
