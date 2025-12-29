import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { Container, Row, Col, Card, Form, Button, Alert } from 'react-bootstrap';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

export default function ForgotPasswordPage() {
  const { tenantSlug } = useParams();

  const tenantMap = {
    timhortons: '60716764-6f96-4ff7-a30d-08b9dacf1591',
    starbucks: 'a1b2c3d4-5678-90ef-ghij-klmnopqrstuv',
  };

  const tenantId = tenantMap[tenantSlug];

  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async e => {
    e.preventDefault();
    if (!email) {
      setMessage('Please enter your email.');
      return;
    }

    setLoading(true);
    setMessage('');

    try {
      const res = await fetch(`${API_BASE_URL}/api/auth/forgot-password`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'X-Tenant-ID': tenantId
        },
        body: JSON.stringify({ email: email.toLowerCase() }),
      });

      const responseText = await res.text();
      if (res.ok) {
        setMessage('✅ Reset link sent! Check your email.');
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
        <Col md={6}>
          <Card>
            <Card.Body>
              <Card.Title className="mb-4 text-center">Forgot Password</Card.Title>
              <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                  <Form.Label>Email</Form.Label>
                  <Form.Control
                    type="email"
                    placeholder="Enter your email"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                    required
                  />
                </Form.Group>

                <Button type="submit" variant="primary" disabled={loading} className="w-100">
                  {loading ? 'Sending...' : 'Send Reset Link'}
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
