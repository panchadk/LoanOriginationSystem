import React from 'react';
import { Accordion, Nav } from 'react-bootstrap';
import { Link, useLocation } from 'react-router-dom';

const Sidebar = () => {
  const location = useLocation();

  return (
    <div className="sidebar">
      <Accordion defaultActiveKey="0" className="accordion-blue">
        {/* User Management */}
        <Accordion.Item eventKey="0">
          <Accordion.Header>User Management</Accordion.Header>
          <Accordion.Body>
            <Nav className="flex-column">
              <Nav.Link
                as={Link}
                to="/admin/users"
                active={location.pathname === '/admin/users'}
              >
                View Users
              </Nav.Link>
              <Nav.Link
                as={Link}
                to="/admin/users/add"
                active={location.pathname === '/admin/users/add'}
              >
                Add User
              </Nav.Link>
              <Nav.Link
                as={Link}
                to="/admin/users/edit"
                active={location.pathname === '/admin/users/edit'}
              >
                Edit User
              </Nav.Link>
              <Nav.Link
                as={Link}
                to="/admin/users/delete"
                active={location.pathname === '/admin/users/delete'}
              >
                Delete User
              </Nav.Link>
            </Nav>
          </Accordion.Body>
        </Accordion.Item>

        {/* Role Management */}
        <Accordion.Item eventKey="1">
          <Accordion.Header>Role Management</Accordion.Header>
          <Accordion.Body>
            <Nav className="flex-column">
              <Nav.Link
                as={Link}
                to="/admin/roles"
                active={location.pathname === '/admin/roles'}
              >
                View Roles
              </Nav.Link>
              <Nav.Link
                as={Link}
                to="/admin/roles/add"
                active={location.pathname === '/admin/roles/add'}
              >
                Add Role
              </Nav.Link>
              <Nav.Link
                as={Link}
                to="/admin/roles/edit"
                active={location.pathname === '/admin/roles/edit'}
              >
                Edit Role
              </Nav.Link>
              <Nav.Link
                as={Link}
                to="/admin/roles/delete"
                active={location.pathname === '/admin/roles/delete'}
              >
                Delete Role
              </Nav.Link>
            </Nav>
          </Accordion.Body>
        </Accordion.Item>
      </Accordion>
    </div>
  );
};

export default Sidebar;