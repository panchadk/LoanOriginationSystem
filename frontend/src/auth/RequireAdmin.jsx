import { useParams, Navigate } from 'react-router-dom';

export default function RequireAdmin({ children }) {
  const { tenantSlug } = useParams();

  // ✅ Read resolved role names from localStorage
  const resolvedRoles = JSON.parse(localStorage.getItem('resolvedRoles') || '[]');
  console.log('Resolved roles:', resolvedRoles);

  const isAdmin = resolvedRoles.includes('ADMIN');

  if (isAdmin) {
    return children;
  }

  // ✅ Redirect based on role count
  if (resolvedRoles.length === 1) {
    return <Navigate to={`/${tenantSlug}/dashboard`} />;
  } else if (resolvedRoles.length > 1) {
    return <Navigate to={`/${tenantSlug}/choose-dashboard`} />;
  }

  // 🚫 No valid roles
  return <Navigate to={`/${tenantSlug}/unauthorized`} />;
}
