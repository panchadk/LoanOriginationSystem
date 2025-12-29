// utils/auth.js
export function decodeToken(token) {
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return {
      email: payload.sub,
      roles: payload.roles || [],
      tenantId: payload.tenant_id,
    };
  } catch (err) {
    console.error('Invalid token', err);
    return null;
  }
}
