import { useAuth } from '../utils/useAuth';
import { Container, Button, Row, Col, Alert, Accordion, Nav } from 'react-bootstrap';
import { useNavigate, useParams, Outlet } from 'react-router-dom';
import { useEffect, useState } from 'react';

export default function AdminDashboard() {
  const user = useAuth();
  const navigate = useNavigate();
  const { tenantSlug } = useParams();
  const [tokenExpired, setTokenExpired] = useState(false);

  const handleLogout = async () => {
    const token = localStorage.getItem('token');

    try {
      await fetch(`${process.env.REACT_APP_API_BASE_URL}/api/auth/logout`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
          'X-Tenant-ID': tenantSlug,
        },
      });
    } catch (err) {
      console.error('Logout failed:', err);
    }

    // ✅ Clear session data
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('resolvedRoles');
    localStorage.removeItem('pendingRedirect');

    // ✅ Redirect to login
    navigate(`/${tenantSlug}/login`);
  };

  useEffect(() => {
    const interval = setInterval(async () => {
      const token = localStorage.getItem('token');
      if (!token) return;

      try {
        const res = await fetch(`${process.env.REACT_APP_API_BASE_URL}/api/auth/heartbeat`, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'X-Tenant-ID': tenantSlug,
          },
        });

        if (!res.ok) {
          console.warn('Session expired');
          setTokenExpired(true);
        }
      } catch (err) {
        console.error('Heartbeat error:', err);
        setTokenExpired(true);
      }
    }, 5 * 60 * 1000); // every 5 minutes

    return () => clearInterval(interval);
  }, [tenantSlug]);

  return (
    <Container fluid className="mt-3">
      {/* Header Row */}
      <Row className="align-items-center mb-4">
        <Col>
          <h3>Admin Dashboard</h3>
        </Col>
        <Col className="text-end">
          <Button variant="outline-danger" onClick={handleLogout}>
            Logout
          </Button>
        </Col>
      </Row>

      {/* Token Expired Alert */}
      {tokenExpired && (
        <Alert variant="danger">
          ⚠️ Your session has expired. Please logout and login again.
        </Alert>
      )}

      {/* Sidebar and Content Area */}
      <Row>
        {/* Sidebar */}
        <Col md={2} className="p-0 bg-light border-end sidebar">
          <Accordion defaultActiveKey="0" className="accordion-blue">
            {/* User Management */}
            <Accordion.Item eventKey="0">
              <Accordion.Header>User Management</Accordion.Header>
              <Accordion.Body>
                <Nav className="flex-column">
                  <Nav.Link href={`/${tenantSlug}/admin/users`}>View Users</Nav.Link>
                  <Nav.Link href={`/${tenantSlug}/admin/users/add`}>Add User</Nav.Link>
                  <Nav.Link href={`/${tenantSlug}/admin/users/edit/:userId`}>Edit User</Nav.Link>
                  <Nav.Link href={`/${tenantSlug}/admin/users/delete/:userId`}>Delete User</Nav.Link>
                </Nav>
              </Accordion.Body>
            </Accordion.Item>

            {/* Role Management */}
            <Accordion.Item eventKey="1">
              <Accordion.Header>Role Management</Accordion.Header>
              <Accordion.Body>
                <Nav className="flex-column">
                  <Nav.Link href={`/${tenantSlug}/admin/roles`}>View Roles</Nav.Link>
                  <Nav.Link href={`/${tenantSlug}/admin/roles/add`}>Add Role</Nav.Link>
                  <Nav.Link href={`/${tenantSlug}/admin/roles/edit/:roleId`}>Edit Role</Nav.Link>
                  <Nav.Link href={`/${tenantSlug}/admin/roles/delete/:roleId`}>Delete Role</Nav.Link>
                </Nav>
              </Accordion.Body>
            </Accordion.Item>

            {/* Tenant Management */}
            <Accordion.Item eventKey="2">
              <Accordion.Header>Tenant Management</Accordion.Header>
              <Accordion.Body>
                <Nav className="flex-column">
                  <Nav.Link href={`/${tenantSlug}/admin/tenants`}>View Tenants</Nav.Link>
                  <Nav.Link href={`/${tenantSlug}/admin/tenants/add`}>Add Tenant</Nav.Link>
                  <Nav.Link href={`/${tenantSlug}/admin/tenants/edit/:tenantId`}>Edit Tenant</Nav.Link>
                  <Nav.Link href={`/${tenantSlug}/admin/tenants/delete/:tenantId`}>Delete Tenant</Nav.Link>
                </Nav>
              </Accordion.Body>
            </Accordion.Item>
          </Accordion>
        </Col>

        {/* Main Content Area */}
        <Col md={10} className="content-area p-4">
          <Outlet />
        </Col>
      </Row>
    </Container>
  );
}