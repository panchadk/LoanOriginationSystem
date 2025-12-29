import { useState } from 'react';
import { useSearchParams, useParams } from 'react-router-dom';
import { Container, Row, Col, Card, Form, Button, Alert } from 'react-bootstrap';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

export default function ResetPasswordPage() {
  const [searchParams] = useSearchParams();
  const { tenantSlug } = useParams();
  const token = searchParams.get('token');

  const tenantMap = {
    timhortons: '60716764-6f96-4ff7-a30d-08b9dacf1591',
    starbucks: 'a1b2c3d4-5678-90ef-ghij-klmnopqrstuv',
  };

  const tenantId = tenantMap[tenantSlug];

  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async e => {
    e.preventDefault();
    if (!newPassword || !confirmPassword) {
      setMessage('Please fill in both fields.');
      return;
    }
    if (newPassword !== confirmPassword) {
      setMessage('Passwords do not match.');
      return;
    }
    if (!token) {
      setMessage('Missing token. Please use the link from your email.');
      return;
    }

    setLoading(true);
    setMessage('');

    try {
      const res = await fetch(`${API_BASE_URL}/api/auth/reset-password`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'X-Tenant-ID': tenantId
        },
        body: JSON.stringify({ token, newPassword }),
      });

      const responseText = await res.text();
      if (res.ok) {
        setMessage('✅ Password reset successful. You can now log in.');
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
              <Card.Title className="mb-4 text-center">Reset Password</Card.Title>
              <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                  <Form.Label>New Password</Form.Label>
                  <Form.Control
                    type="password"
                    placeholder="Enter new password"
                    value={newPassword}
                    onChange={e => setNewPassword(e.target.value)}
                    required
                  />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Confirm Password</Form.Label>
                  <Form.Control
                    type="password"
                    placeholder="Confirm new password"
                    value={confirmPassword}
                    onChange={e => setConfirmPassword(e.target.value)}
                    required
                  />
                </Form.Group>

                <Button type="submit" variant="primary" disabled={loading} className="w-100">
                  {loading ? 'Resetting...' : 'Reset Password'}
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
