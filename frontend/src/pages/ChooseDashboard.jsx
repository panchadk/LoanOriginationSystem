import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Container, Button } from 'react-bootstrap';

export default function ChooseDashboard() {
  const navigate = useNavigate();
  const { tenantSlug } = useParams();
  const [user, setUser] = useState(null);

  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  }, []);

  if (!user) {
    return <p className="text-center mt-5">Loading user info...</p>;
  }

  const roleNames = Array.isArray(user.roles) ? user.roles : [user.roles];

  return (
    <Container className="mt-5 text-center">
      <h4>Welcome, {user.name}</h4>
      <p>You have multiple roles. Choose your dashboard:</p>

      {roleNames.includes('ADMIN') && (
        <Button variant="primary" className="m-2" onClick={() => navigate(`/${tenantSlug}/admin`)}>
          Admin Dashboard
        </Button>
      )}

      <Button variant="secondary" className="m-2" onClick={() => navigate(`/${tenantSlug}/dashboard`)}>
        Main Dashboard
      </Button>
    </Container>
  );
}
