import { useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Container, Row, Col, Card, Form, Button, Alert } from 'react-bootstrap';
import { decodeToken } from '../utils/auth';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

export default function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const { tenantSlug } = useParams();

  const validateForm = () => {
    if (!email || !password) {
      setMessage('Please enter both email and password.');
      return false;
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email) || email.length < 5) {
      setMessage('Please enter a valid email address.');
      return false;
    }
    return true;
  };

  const handleLogin = async () => {
    if (!validateForm()) return;

    setLoading(true);
    setMessage('');

    try {
      const res = await fetch(`${API_BASE_URL}/api/auth/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'X-Tenant-ID': tenantSlug
        },
        body: JSON.stringify({ email, password }),
      });

      if (res.ok) {
        const data = await res.json();
        localStorage.setItem('token', data.token);

        const user = decodeToken(data.token);
        if (!user) {
          setMessage('❌ Invalid token received.');
          return;
        }

        localStorage.setItem('user', JSON.stringify(user));
       // localStorage.setItem('tenantId', tenantSlug); // ✅ Store tenant ID here as Slug ex timhorton
        localStorage.setItem('tenantId',user.tenantId);    //Store as UUID

        setMessage('✅ Login successful!');

        // ✅ Extract roles
        const roleNames = Array.isArray(user.roles) ? user.roles : [user.roles];
        localStorage.setItem('resolvedRoles', JSON.stringify(roleNames));
        console.log('✅ Roles from token:', roleNames);

        // 🚦 Redirect logic
        if (roleNames.length > 1) {
          navigate(`/${tenantSlug}/choose-dashboard`);
        } else if (roleNames.includes('ADMIN')) {
          navigate(`/${tenantSlug}/admin`);
        } else {
          navigate(`/${tenantSlug}/dashboard`);
        }
      } else {
        setMessage('❌ Login failed. Please check your credentials.');
      }
    } catch (err) {
      console.error('Login error:', err);
      setMessage('❌ Server error. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container className="mt-5">
      <Row className="justify-content-center">
        <Col md={6}>
          <Card>
            <Card.Body>
              <Card.Title className="mb-4 text-center">Login</Card.Title>
              <Form onSubmit={e => { e.preventDefault(); handleLogin(); }}>
                <Form.Group className="mb-3">
                  <Form.Label>Email</Form.Label>
                  <Form.Control
                    type="email"
                    placeholder="Enter email"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                    required
                  />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Password</Form.Label>
                  <Form.Control
                    type="password"
                    placeholder="Enter password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                    required
                  />
                </Form.Group>

                <Button variant="primary" type="submit" disabled={loading} className="w-100">
                  {loading ? 'Logging in...' : 'Login'}
                </Button>
              </Form>

              {message && (
                <Alert variant={message.startsWith('✅') ? 'success' : 'danger'} className="mt-3">
                  {message}
                </Alert>
              )}

              <div className="mt-3 text-center">
                <Link to={`/${tenantSlug}/register`}>New user? Register here</Link>
                <br />
                <Link to={`/${tenantSlug}/forgot-password`}>Forgot your password?</Link>
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}
