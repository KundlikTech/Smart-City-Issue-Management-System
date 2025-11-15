import axios from "axios";
import { getToken } from "./auth"; // ✔ Fixed missing import

const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

// Automatically attach JWT token
api.interceptors.request.use(
  (config) => {
    const token = getToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default api;
