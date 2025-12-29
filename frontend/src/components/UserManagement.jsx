import React, { useEffect, useMemo, useState } from 'react';
import { useTable, usePagination,useSortBy } from 'react-table';
import axios from 'axios';
import { Button, Modal, Form, Table, Alert } from 'react-bootstrap';


const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;





const UserManagement = () => {
  const [users, setUsers] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    firstName: '',
    lastName: '',
    tenantId: '',
    tenantSlug: '',
    status: ''
  });
  const [selectedRoles, setSelectedRoles] = useState([]);
  const [editingUserId, setEditingUserId] = useState(null);
  const [tenants, setTenants] = useState([]);
  const [roles, setRoles] = useState([]);
  const [message, setMessage] = useState('');

  const token = localStorage.getItem('token');


 const fetchUsers = async () => {
    try {
      const tenantId = JSON.parse(localStorage.getItem('user'))?.tenantId;
      const res = await axios.get(`${API_BASE_URL}/api/admin/users`, {
        headers: {
          Authorization: `Bearer ${token}`,
          'X-Tenant-ID': tenantId,
        },
      });
      const userList = res.data?.users || [];
      setUsers(userList);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  const loadDropdowns = async () => {
    try {
      const [tenantRes, roleRes] = await Promise.all([
        axios.get(`${API_BASE_URL}/api/tenants`, {
          headers: { Authorization: `Bearer ${token}` },
        }),
        axios.get(`${API_BASE_URL}/api/roles`, {
          headers: { Authorization: `Bearer ${token}` },
        }),
      ]);
      setTenants(tenantRes.data);
      setRoles(roleRes.data);
    } catch (err) {
      console.error('Failed to load tenants or roles:', err);
    }
  };


useEffect(() => {
  fetchUsers();
  loadDropdowns();
}, []);




  const handleInputChange = (e) => {
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

  const handleRoleSelect = (e) => {
    const selectedOptions = Array.from(e.target.selectedOptions).map(opt => opt.value);
    setSelectedRoles(selectedOptions);
  };

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

const [searchTerm, setSearchTerm] = useState('');
const [filteredUsers, setFilteredUsers] = useState([]);

useEffect(() => {
  setFilteredUsers(users); // initialize
}, [users]);

const handleSearch = () => {
  const term = searchTerm.toLowerCase();
  const filtered = users.filter((user) => {
    return (
      user.username?.toLowerCase().includes(term) ||
      user.email?.toLowerCase().includes(term) ||
      user.firstName?.toLowerCase().includes(term) ||
      user.lastName?.toLowerCase().includes(term) ||
      (Array.isArray(user.roles) && user.roles.join(',').toLowerCase().includes(term))
    );
  });
  setFilteredUsers(filtered);
};

const clearSearch = () => {
  setSearchTerm('');
  setFilteredUsers(users);
};


const getRoleNames = (roleIds) => {
  if (!Array.isArray(roleIds)) return '—';
  return roleIds
    .map(id => {
      const role = roles.find(r => r.roleId === id);
      return role?.name || '';
    })
    .filter(name => name)
    .join(', ');
};

const getTenantName = (tenantId) => {
  const tenant = tenants.find(t => t.tenantId === tenantId);
  return tenant?.name || '—';
};




  const handleSave = async () => {
    if (!validateForm()) return;

    const payload = {
      username: formData.username.toLowerCase(),
      email: formData.email.toLowerCase(),
      password: formData.password,
      firstName: formData.firstName,
      lastName: formData.lastName,
      tenantId: formData.tenantId,
      tenantSlug: formData.tenantSlug,
      roles: selectedRoles,
      status: formData.status
    };

    try {
      const url = editingUserId
        ? `${API_BASE_URL}/api/admin/users/${editingUserId}`
        : `${API_BASE_URL}/api/admin/users`;

      const method = editingUserId ? 'put' : 'post';

      const res = await axios({
        method,
        url,
        headers: {
          Authorization: `Bearer ${token}`,
          'X-Tenant-ID': formData.tenantId,
          'Content-Type': 'application/json',
        },
        data: payload,
      });

      if (res.status === 200 || res.status === 201) {
        setMessage(editingUserId ? '✅ User updated successfully!' : '✅ User added successfully!');
        setShowModal(false);
        fetchUsers();
      } else {
        setMessage(`❌ ${res.statusText}`);
      }
    } catch (err) {
      setMessage('❌ Server error. Please try again later.');
      console.error(err);
    }
  };


  const columns = useMemo(
    () => [
      { Header: 'Username', accessor: 'username' },
      { Header: 'First Name', accessor: 'firstName' },
      { Header: 'Last Name', accessor: 'lastName' },
      { Header: 'Email', accessor: 'email' },
      {
        Header: 'Roles',
        accessor: row => getRoleNames(row.roles),
      },
      { Header: 'Status', accessor: 'status' },
      {
        Header: 'Tenant',
        accessor: row => getTenantName(row.tenantId),
      },
      {
        Header: 'Actions',
        Cell: ({ row }) => (
          <>
            <Button
              variant="warning"
              size="sm"
              className="me-2"
              onClick={async () => {
                await loadDropdowns();

                const user = row.original;
                const selectedTenant = tenants.find(t => t.tenantId === user.tenantId);
                const tenantSlug = selectedTenant?.slug || '';

                setFormData({
                  id: user.id,
                  username: user.username,
                  email: user.email,
                  password: '',
                  confirmPassword: '',
                  firstName: user.firstName,
                  lastName: user.lastName,
                  tenantId: user.tenantId,
                  tenantSlug,
                  status: user.status || '',
                });

                setSelectedRoles(user.roles || []);
                setEditingUserId(user.id);
                setMessage('');
                setShowModal(true);
              }}
            >
              Edit
            </Button>
            <Button
              variant="danger"
              size="sm"
              onClick={() => handleDelete(row.original.id)}
            >
              Delete
            </Button>
          </>
        ),
      },
    ],
    [roles, tenants]
  );

 const handleDelete = async (userId) => {
   const confirmDelete = window.confirm('Are you sure you want to delete this user?');
   if (!confirmDelete) return;

   try {
     const res = await axios.delete(`${API_BASE_URL}/api/admin/users/${userId}`, {
       headers: {
         Authorization: `Bearer ${token}`,
       },
     });

     if (res.status === 200) {
       setMessage('✅ User deleted successfully!');
       fetchUsers(); // refresh the list
     } else {
       setMessage(`❌ ${res.statusText}`);
     }
   } catch (err) {
     setMessage('❌ Server error. Please try again later.');
     console.error(err);
   }
 };


//  const data = useMemo(() => users, [users]);
  const data = useMemo(() => filteredUsers, [filteredUsers]);


  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    rows,
    page,
    prepareRow,
    canPreviousPage,
    canNextPage,
    pageOptions,
    pageCount,
    gotoPage,
    nextPage,
    previousPage,
    state: { pageIndex },
  }  = useTable(
      {
        columns,
        data,
        initialState: { sortBy: [{ id: 'username', desc: false }] },
      },
      useSortBy,
      usePagination
    );

  return (
    <div className="mt-4">
      <h4>User Management</h4>

      <Button
        variant="primary"
        className="mb-3"
        onClick={() => {
          setFormData({
            username: '',
            email: '',
            password: '',
            confirmPassword: '',
            firstName: '',
            lastName: '',
            tenantId: '',
            tenantSlug: '',
            status: ''
          });
          setSelectedRoles([]);
          setEditingUserId(null);
          setMessage('');
          loadDropdowns();
          setShowModal(true);
        }}
      >
        Add User
      </Button>

         {/* 🔍 Step 1: Search Form */}
            <Form className="d-flex mb-3">
              <Form.Control
                type="text"
                placeholder="Search by name, email, or role"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
              <Button variant="secondary" className="ms-2" onClick={handleSearch}>
                Search
              </Button>
              <Button variant="outline-secondary" className="ms-2" onClick={clearSearch}>
                Clear
              </Button>
            </Form>


      <Table striped bordered hover responsive {...getTableProps()}>
       <thead>
         {headerGroups.map(headerGroup => (
           <tr {...headerGroup.getHeaderGroupProps()}>
             {headerGroup.headers.map(column => (
               <th {...column.getHeaderProps(column.getSortByToggleProps())}>
                 {column.render('Header')}
                 <span>
                   {column.isSorted
                     ? column.isSortedDesc
                       ? ' 🔽'
                       : ' 🔼'
                     : ''}
                 </span>
               </th>
             ))}
           </tr>
         ))}
       </thead>

        <tbody {...getTableBodyProps()}>
          {page.map((row) => {
            prepareRow(row);
            return (
              <tr key={row.id} {...row.getRowProps()}>
                {row.cells.map((cell) => (
                  <td key={cell.column.id} {...cell.getCellProps()}>
                    {cell.render('Cell')}
                  </td>
                ))}
              </tr>
            );
          })}
        </tbody>
      </Table>

      <div className="d-flex justify-content-center mt-3 flex-wrap gap-2">
        <Button onClick={() => gotoPage(0)} disabled={!canPreviousPage} size="sm">
          First
        </Button>
        <Button onClick={() => previousPage()} disabled={!canPreviousPage} size="sm">
          Previous
        </Button>
        <span className="mx-2 align-self-center">
          Page {pageIndex + 1} of {pageOptions.length}
        </span>
        <Button onClick={() => nextPage()} disabled={!canNextPage} size="sm">
          Next
        </Button>
        <Button onClick={() => gotoPage(pageCount - 1)} disabled={!canNextPage} size="sm">
          Last
        </Button>
      </div>


           {message && (
             <Alert variant={message.startsWith('✅') ? 'success' : 'danger'} className="mt-3">
               {message}
             </Alert>
           )}

           <Modal show={showModal} onHide={() => setShowModal(false)}>
             <Modal.Header closeButton>
               <Modal.Title>{editingUserId ? 'Edit User' : 'Add User'}</Modal.Title>
             </Modal.Header>
             <Modal.Body>
               <Form>
                 <Form.Group className="mb-3">
                   <Form.Control
                     name="username"
                     placeholder="Username"
                     value={formData.username}
                     onChange={handleInputChange}
                     required
                   />
                 </Form.Group>
                 <Form.Group className="mb-3">
                   <Form.Control
                     name="firstName"
                     placeholder="First Name"
                     value={formData.firstName}
                     onChange={handleInputChange}
                     required
                   />
                 </Form.Group>
                 <Form.Group className="mb-3">
                   <Form.Control
                     name="lastName"
                     placeholder="Last Name"
                     value={formData.lastName}
                     onChange={handleInputChange}
                     required
                   />
                 </Form.Group>
                 <Form.Group className="mb-3">
                   <Form.Control
                     type="email"
                     name="email"
                     placeholder="Email"
                     value={formData.email}
                     onChange={handleInputChange}
                     required
                   />
                 </Form.Group>
                 <Form.Group className="mb-3">
                   <Form.Control
                     type="password"
                     name="password"
                     placeholder="Password"
                     value={formData.password}
                     onChange={handleInputChange}
                     required
                   />
                 </Form.Group>
                 <Form.Group className="mb-3">
                   <Form.Control
                     type="password"
                     name="confirmPassword"
                     placeholder="Confirm Password"
                     value={formData.confirmPassword}
                     onChange={handleInputChange}
                     required
                   />
                 </Form.Group>
                 <Form.Group className="mb-3">
                 <Form.Select
                   name="tenantId"
                   value={formData.tenantId}
                   onChange={handleInputChange}
                   required
                 >
                   <option value="">Select Tenant</option>
                   {tenants.map(t => (
                     <option key={t.tenantId} value={t.tenantId}>
                       {t.name}
                     </option>
                   ))}
                 </Form.Select>

                 </Form.Group>
                 <Form.Group className="mb-3">
                  <Form.Select multiple onChange={handleRoleSelect} value={selectedRoles}>
                    {roles.map(role => (
                      <option key={role.roleId} value={role.roleId}>
                        {role.name}
                      </option>
                    ))}
                  </Form.Select>

                 </Form.Group>
                 <Form.Group className="mb-3">
                   <Form.Control
                     name="status"
                     placeholder="Status"
                     value={formData.status}
                     onChange={handleInputChange}
                   />
                 </Form.Group>
               </Form>
             </Modal.Body>
             <Modal.Footer>
               <Button variant="secondary" onClick={() => setShowModal(false)}>
                 Cancel
               </Button>
               <Button variant="primary" onClick={handleSave}>
                 Save
               </Button>
             </Modal.Footer>
           </Modal>
         </div>
       );
     };

     export default UserManagement;
