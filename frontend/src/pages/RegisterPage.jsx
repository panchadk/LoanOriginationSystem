import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Row, Col, Card, Form, Button, Alert } from 'react-bootstrap';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

export default function RegisterPage() {
  const [tenants, setTenants] = useState([]);
  const [roles, setRoles] = useState([]);
  const [selectedRoles, setSelectedRoles] = useState([]);
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    firstName: '',
    lastName: '',
    tenantId: '',
    tenantSlug: ''
  });
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const loadData = async () => {
      try {
        const tenantRes = await fetch(`${API_BASE_URL}/api/tenants`);
        const roleRes = await fetch(`${API_BASE_URL}/api/roles`);
        if (!tenantRes.ok || !roleRes.ok) throw new Error('Fetch failed');

        const tenantsData = await tenantRes.json();
        const rolesData = await roleRes.json();
        setTenants(tenantsData);
        setRoles(rolesData);
      } catch (err) {
        setMessage('❌ Failed to load tenants or roles.');
        console.error(err);
      }
    };
    loadData();
  }, []);

  const validateForm = () => {
    const { username, email, password, confirmPassword, firstName, lastName, tenantId } = formData;
    if (!username || !email || !password || !confirmPassword || !firstName || !lastName || !tenantId || selectedRoles.length === 0) {
      setMessage('Please fill in all fields and select at least one role.');
      return false;
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setMessage('Please enter a valid email address.');
      return false;
    }
    if (password !== confirmPassword) {
      setMessage('Passwords do not match.');
      return false;
    }
    return true;
  };

  const handleChange = e => {
    const { name, value } = e.target;

    if (name === 'tenantId') {
      const selectedTenant = tenants.find(t => t.tenantId === value);
      setFormData(prev => ({
        ...prev,
        tenantId: value,
        tenantSlug: selectedTenant?.slug || ''
      }));
    } else {
      setFormData(prev => ({ ...prev, [name]: value }));
    }
  };

  const handleRoleSelect = e => {
    const selectedOptions = Array.from(e.target.selectedOptions).map(opt => opt.value);
    setSelectedRoles(selectedOptions);
  };

  const handleSubmit = async e => {
    e.preventDefault();
    if (!validateForm()) return;

    setLoading(true);
    setMessage('');

    try {
      const res = await fetch(`${API_BASE_URL}/api/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          username: formData.username.toLowerCase(),
          email: formData.email.toLowerCase(),
          password: formData.password,
          firstName: formData.firstName,
          lastName: formData.lastName,
          tenantId: formData.tenantId,
          tenantSlug: formData.tenantSlug,
          roles: selectedRoles
        }),
      });

      const responseText = await res.text();

      if (res.ok) {
        setMessage('✅ Registration successful! Redirecting to login...');
        setTimeout(() => navigate('/login'), 1500);
      } else {
        setMessage(`❌ ${responseText}`);
      }
    } catch (err) {
      setMessage('❌ Server error. Please try again later.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container className="mt-5">
      <Row className="justify-content-center">
        <Col md={8}>
          <Card>
            <Card.Body>
              <Card.Title className="mb-4 text-center">Register</Card.Title>
              <Form onSubmit={handleSubmit}>
                <Row>
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Control name="username" placeholder="Username" onChange={handleChange} required />
                    </Form.Group>
                    <Form.Group className="mb-3">
                      <Form.Control name="firstName" placeholder="First Name" onChange={handleChange} required />
                    </Form.Group>
                    <Form.Group className="mb-3">
                      <Form.Control name="lastName" placeholder="Last Name" onChange={handleChange} required />
                    </Form.Group>
                    <Form.Group className="mb-3">
                      <Form.Control type="email" name="email" placeholder="Email" onChange={handleChange} required />
                    </Form.Group>
                  </Col>
                  <Col md={6}>
                    <Form.Group className="mb-3">
                      <Form.Control type="password" name="password" placeholder="Password" onChange={handleChange} required />
                    </Form.Group>
                    <Form.Group className="mb-3">
                      <Form.Control type="password" name="confirmPassword" placeholder="Confirm Password" onChange={handleChange} required />
                    </Form.Group>
                    <Form.Group className="mb-3">
                      <Form.Select name="tenantId" onChange={handleChange} required>
                        <option value="">Select Tenant</option>
                        {tenants.map(t => (
                          <option key={t.tenantId} value={t.tenantId}>{t.name}</option>
                        ))}
                      </Form.Select>
                    </Form.Group>
                    <Form.Group className="mb-3">
                      <Form.Select multiple onChange={handleRoleSelect}>
                        {roles.map(role => (
                          <option key={role.roleId} value={role.roleId}>{role.name}</option>
                        ))}
                      </Form.Select>
                    </Form.Group>
                  </Col>
                </Row>
                <Button type="submit" variant="primary" disabled={loading} className="w-100">
                  {loading ? 'Registering...' : 'Register'}
                </Button>
              </Form>
              {message && (
                <Alert variant={message.startsWith('✅') ? 'success' : 'danger'} className="mt-3">
                  {message}
                </Alert>
              )}
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}
