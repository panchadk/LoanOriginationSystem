// src/pages/UnauthorizedPage.jsx
import { Container, Alert } from 'react-bootstrap';

export default function UnauthorizedPage() {
  return (
    <Container className="mt-5 text-center">
      <Alert variant="danger">
        <h4>🚫 Access Denied</h4>
        <p>You don’t have permission to view this page.</p>
      </Alert>
    </Container>
  );
}
