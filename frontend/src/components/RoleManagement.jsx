import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Button, Modal, Form, Table, Alert, Badge } from 'react-bootstrap';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const RoleManagement = () => {
  const [roles, setRoles] = useState([]);
  const [tenants, setTenants] = useState([]);
  const [formData, setFormData] = useState({ name: '', tenantId: '' });
  const [showModal, setShowModal] = useState(false);
  const [message, setMessage] = useState('');
  const token = localStorage.getItem('token');

  useEffect(() => {
    fetchRoles();
    fetchTenants();
  }, []);

  const fetchRoles = async () => {
    try {
      const res = await axios.get(`${API_BASE_URL}/api/roles`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setRoles(res.data);
      console.log('Roles:', res.data); // inside fetchRoles


    } catch (err) {
      console.error('Failed to load roles:', err);
    }
  };

  const fetchTenants = async () => {
    try {
      const res = await axios.get(`${API_BASE_URL}/api/tenants`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setTenants(res.data);
      console.log('Tenants:', res.data); // inside fetchTenants
    } catch (err) {
      console.error('Failed to load tenants:', err);
    }
  };

  const handleCreate = async () => {
    if (!formData.name || !formData.tenantId) {
      setMessage('Please enter a role name and select a tenant.');
      return;
    }

    try {
      const res = await axios.post(`${API_BASE_URL}/api/roles`, formData, {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (res.status === 201 || res.status === 200) {
        setMessage('');
        setShowModal(false);
        setFormData({ name: '', tenantId: '' });
        fetchRoles();
      } else {
        setMessage(`❌ ${res.statusText}`);
      }
    } catch (err) {
      setMessage('❌ Server error. Please try again later.');
      console.error(err);
    }
  };

  return (
    <div className="container mt-4">
      <h2>Role Management</h2>

      <div className="d-flex justify-content-between align-items-center mb-3">
        <Button variant="success" onClick={() => setShowModal(true)}>
          + Create Role
        </Button>
        {message && (
          <Alert variant="danger" className="mb-0">
            {message}
          </Alert>
        )}
      </div>

      {!tenants.length ? (
        <div>Loading tenants...</div>
      ) : (
       <Table striped bordered hover responsive>
         <thead>
           <tr>
             <th>#</th>
             <th>Role Name</th>
             <th>Tenant</th>
           </tr>
         </thead>
         <tbody>
           {roles.map((role, index) => {
             // Normalize both IDs to lowercase strings for comparison
             const roleTenantId = String(role.tenantId || '').toLowerCase();
             const tenant = tenants.find(t => String(t.tenantId || '').toLowerCase() === roleTenantId);

             return (
               <tr key={role.roleId}>
                 <td>{index + 1}</td>
                 <td>
                   <Badge bg="info" className="text-uppercase">
                     {role.name}
                   </Badge>
                 </td>
                 <td>
                   {tenant?.name ? tenant.name : (
                     <span className="text-danger">
                       Unknown ({role.tenantId || 'missing'})
                     </span>
                   )}
                 </td>
               </tr>
             );
           })}
         </tbody>
       </Table>

      )}

      {/* Modal */}
      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Create New Role</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Role Name</Form.Label>
              <Form.Control
                type="text"
                name="name"
                value={formData.name}
                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Select Tenant</Form.Label>
              <Form.Select
                name="tenantId"
                value={formData.tenantId}
                onChange={(e) => setFormData({ ...formData, tenantId: e.target.value })}
              >
                <option value="">Select a tenant</option>
                {tenants.map((tenant) => (
                  <option key={tenant.tenantId} value={tenant.tenantId}>
                    {tenant.name}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
          </Form>
          {message && <Alert variant="danger">{message}</Alert>}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowModal(false)}>Cancel</Button>
          <Button variant="primary" onClick={handleCreate}>Create</Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default RoleManagement;
