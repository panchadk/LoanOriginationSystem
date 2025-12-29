import { decodeToken } from "./auth";

export function useAuth() {
  const token = localStorage.getItem("token");
  const decoded = decodeToken(token);

  if (!decoded) return { tenantId: null, userId: null };

  // Optional: check expiry
  const isExpired = decoded.exp && Date.now() >= decoded.exp * 1000;

  return {
    tenantId: decoded.tenantId,
    userId: decoded.userId,
    isExpired
  };
}
