import { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Form, Button, Alert, Table } from 'react-bootstrap';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

export default function TenantManagement() {
  const [tenants, setTenants] = useState([]);
  const [name, setName] = useState('');
  const [slug, setSlug] = useState('');
  const [createdBy, setCreatedBy] = useState('');
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);

  // ✅ Fetch tenants on load
  useEffect(() => {
    fetch(`${API_BASE_URL}/api/tenants`)
      .then(res => res.json())
      .then(data => setTenants(data))
      .catch(err => console.error('Failed to load tenants', err));
  }, []);

  // ✅ Handle tenant creation
  const handleCreateTenant = async e => {
    e.preventDefault();
    if (!name || !slug || !createdBy) {
      setMessage('Please fill in all fields.');
      return;
    }

    setLoading(true);
    setMessage('');

    try {
      const res = await fetch(`${API_BASE_URL}/api/admin/tenants`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, slug, createdBy })
      });

      if (res.ok) {
        const newTenant = await res.json();
        setTenants(prev => [...prev, newTenant]);
        setName('');
        setSlug('');
        setCreatedBy('');
        setMessage('✅ Tenant created successfully.');
      } else {
        const errorText = await res.text();
        setMessage(`❌ ${errorText}`);
      }
    } catch (err) {
      console.error(err);
      setMessage('❌ Server error. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container className="mt-5">
      <Row>
        <Col md={6}>
          <Card>
            <Card.Body>
              <Card.Title>Create New Tenant</Card.Title>
              <Form onSubmit={handleCreateTenant}>
                <Form.Group className="mb-3">
                  <Form.Label>Tenant Name</Form.Label>
                  <Form.Control
                    type="text"
                    value={name}
                    onChange={e => setName(e.target.value)}
                    placeholder="e.g. Starbucks"
                    required
                  />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Slug</Form.Label>
                  <Form.Control
                    type="text"
                    value={slug}
                    onChange={e => setSlug(e.target.value)}
                    placeholder="e.g. starbucks"
                    required
                  />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Created By</Form.Label>
                  <Form.Control
                    type="text"
                    value={createdBy}
                    onChange={e => setCreatedBy(e.target.value)}
                    placeholder="admin@platform.com"
                    required
                  />
                </Form.Group>

                <Button type="submit" variant="primary" disabled={loading}>
                  {loading ? 'Creating...' : 'Create Tenant'}
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

        <Col md={6}>
          <Card>
            <Card.Body>
              <Card.Title>Existing Tenants</Card.Title>
              <Table striped bordered hover>
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Slug</th>
                    <th>Status</th>
                    <th>Created By</th>
                    <th>Created At</th>
                  </tr>
                </thead>
                <tbody>
                  {tenants.map(t => (
                    <tr key={t.tenantId}>
                      <td>{t.name}</td>
                      <td>{t.slug}</td>
                      <td>{t.status}</td>
                      <td>{t.createdBy}</td>
                      <td>{new Date(t.createdAt).toLocaleString()}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}
