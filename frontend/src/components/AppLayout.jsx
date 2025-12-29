import { useEffect } from 'react';
import { useNavigate, useParams, Outlet } from 'react-router-dom';

export default function AppLayout() {
  const navigate = useNavigate();
  const { tenantSlug } = useParams();

  useEffect(() => {
    const interval = setInterval(async () => {
      const token = localStorage.getItem('token');
      if (!token) return;

      try {
        const res = await fetch(`${process.env.REACT_APP_API_BASE_URL}/api/auth/heartbeat`, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'X-Tenant-ID': tenantSlug
          }
        });

        if (!res.ok) {
          console.warn('Session expired');
          localStorage.clear();
          navigate(`/${tenantSlug}/login`);
        }
      } catch (err) {
        console.error('Heartbeat error:', err);
      }
    }, 5 * 60 * 1000); // every 5 minutes

    return () => clearInterval(interval);
  }, [navigate, tenantSlug]);

  return (
    <div>
      {/* Optional: Add shared header or nav here */}
      <Outlet />
    </div>
  );
}
