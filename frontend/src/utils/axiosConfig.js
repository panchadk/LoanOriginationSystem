import axios from "axios";
import { jwtDecode } from "jwt-decode";

// Create a single Axios instance
const api = axios.create({
  baseURL: "http://localhost:8080/api", // ✅ matches backend prefix
});

// Attach interceptor
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");

    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;

      try {
        const decoded = jwtDecode(token);
        const tenantId = decoded.tenantId; // ✅ camelCase
        const userId = decoded.userId;     // ✅ camelCase

        if (tenantId) {
          config.headers["X-Tenant-ID"] = tenantId;
        }
        if (userId) {
          config.headers["X-User-ID"] = userId;
        }
      } catch (err) {
        console.error("Failed to decode JWT", err);
      }
    }

    return config;
  },
  (error) => Promise.reject(error)
);

export default api;
