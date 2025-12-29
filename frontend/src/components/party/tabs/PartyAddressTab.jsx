import React, { useEffect, useState } from 'react';
import { ExclamationCircleOutlined } from '@ant-design/icons';
import axios from 'axios';
import {
  Table,
  Button,
  Input,
  DatePicker,
  Modal,
  message,
  Select,
} from 'antd';
import dayjs from 'dayjs';
import { v4 as uuidv4 } from 'uuid';

const { Option } = Select;

const PartyAddressTab = ({ partyId }) => {
  const tenantId = localStorage.getItem('tenantId');
  const token = localStorage.getItem('token');
  const [addresses, setAddresses] = useState([]);
  const [editingKey, setEditingKey] = useState('');
  const [modalVisible, setModalVisible] = useState(false);
  const [newAddress, setNewAddress] = useState({
    kind: 'LEGAL',
    line1: '',
    line2: null,
    city: '',
    provinceCode: null,
    postalCode: null,
    countryCode: 'CA',
    effectiveTo: null,
    lat: '0',
    lng: '0',
  });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!token) {
      message.error('Authentication token is missing. Please log in again.');
    }
  }, [token]);

  const fetchAddresses = () => {
    if (!partyId || !tenantId || !token) return;
    setLoading(true);
    axios
      .get(`/api/party/${partyId}/address`, {
        headers: {
          Authorization: `Bearer ${token}`,
          'X-Tenant-ID': tenantId,
        },
      })
      .then((res) => setAddresses(res.data))
      .catch((err) => {
        console.error('❌ Failed to load addresses:', err.response?.data || err.message);
        message.error(`Failed to load addresses: ${err.response?.data?.message || 'Unknown error'}`);
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchAddresses();
  }, [partyId, tenantId, token]);

  const handleSave = (record) => {
    // Find the updated address in the state
    const updatedAddress = addresses.find((a) => a.addressId === record.addressId);

    if (!updatedAddress) {
      message.error('Could not find updated address');
      return;
    }

    // Construct the payload with the latest values
    const payload = {
      ...updatedAddress,
      geocode: {
        lat: parseFloat(updatedAddress.geocode?.lat || '0'),
        lng: parseFloat(updatedAddress.geocode?.lng || '0'),
      },
    };

    // Send the updated payload to the backend
    axios
      .put(`/api/party/${partyId}/address/${updatedAddress.addressId}/${updatedAddress.effectiveFrom}`, payload, {
        headers: {
          Authorization: `Bearer ${token}`,
          'X-Tenant-ID': tenantId,
        },
      })
      .then(() => {
        message.success('Address updated');
        fetchAddresses(); // Refresh the table data
        setEditingKey(''); // Exit edit mode
      })
      .catch((err) => {
        console.error('❌ Update failed:', err.response?.data || err.message);
        message.error(`Update failed: ${err.response?.data?.message || 'Unknown error'}`);
      });
  };



const handleDelete = async (record) => {
  console.log('🎯 handleDelete called with:', record);

  // Validation
  if (!record || !record.addressId || !record.effectiveFrom) {
    message.error('Invalid record. Cannot delete.');
    return;
  }

  if (!partyId || !tenantId || !token) {
    message.error('Missing authentication data.');
    return;
  }

  try {
    // FIRST CONFIRMATION - Standard confirmation
    await Modal.confirm({
      title: 'Delete Address?',
      content: (
        <div>
          <p>Are you sure you want to delete this address?</p>
          <p><strong>{record.line1}, {record.city}</strong></p>
          <p>This action cannot be undone.</p>
        </div>
      ),
      okText: 'Yes, Delete',
      cancelText: 'Cancel',
      okType: 'danger',
      centered: true,
    });

    // SECOND CONFIRMATION - Extra safety for important data
    await Modal.confirm({
      title: 'Please Confirm Again',
      content: (
        <div>
          <p style={{ color: '#ff4d4f', fontWeight: 'bold' }}>
            ⚠️ Final Warning: This will permanently delete the address!
          </p>
          <p>Address: <strong>{record.line1}, {record.city}, {record.provinceCode}</strong></p>
          <p>Type: <strong>{record.kind}</strong></p>
          <p>This cannot be recovered.</p>
        </div>
      ),
      okText: 'Yes, I Understand - Delete Permanently',
      cancelText: 'No, Keep Address',
      okType: 'danger',
      centered: true,
      icon: <ExclamationCircleOutlined style={{ color: '#ff4d4f' }} />,
    });

    console.log('🚀 User confirmed deletion TWICE');

    // Format the effectiveFrom date
    const effectiveFrom = dayjs(record.effectiveFrom).format('YYYY-MM-DD');
    const url = `/api/party/${partyId}/address/${record.addressId}/${effectiveFrom}`;

    console.log('📡 Making DELETE request to:', url);

    // Show loading state
    message.loading('Deleting address...', 0);

    const response = await axios.delete(url, {
      headers: {
        Authorization: `Bearer ${token}`,
        'X-Tenant-ID': tenantId,
      },
      timeout: 10000, // 10 second timeout
    });

    // Hide loading message
    message.destroy();

    console.log('✅ Delete successful:', response.data);
    message.success('Address deleted successfully');

    // Refresh the addresses list
    fetchAddresses();

  } catch (error) {
    // Hide loading message
    message.destroy();

    // Check if it's a modal cancellation (no response property)
    if (!error.response) {
      console.log('❌ User cancelled the deletion');
      return; // User clicked cancel, no error message needed
    }

    // It's an API error
    console.error('❌ Delete API failed:', {
      status: error.response?.status,
      statusText: error.response?.statusText,
      data: error.response?.data,
      url: error.config?.url
    });

    // Handle specific error cases
    switch (error.response?.status) {
      case 404:
        message.error('Address not found. It may have already been deleted.');
        // Remove from local state anyway
        setAddresses(prev => prev.filter(addr => addr.addressId !== record.addressId));
        break;
      case 403:
        message.error('You do not have permission to delete this address.');
        break;
      case 400:
        message.error('Invalid request. Please check the address data.');
        break;
      case 500:
        message.error('Server error. Please try again later.');
        break;
      case 408:
      case 504:
        message.error('Request timeout. Please check your connection and try again.');
        break;
      default:
        message.error(`Delete failed: ${error.response?.data?.message || error.message || 'Unknown error'}`);
    }
  }
};

  const handleAdd = () => {
    const payload = {
      partyId,
      addressId: uuidv4(),
      kind: newAddress.kind || 'LEGAL',
      effectiveFrom: dayjs().format('YYYY-MM-DD'),
      effectiveTo: newAddress.effectiveTo || null,
      line1: newAddress.line1 || '',
      line2: newAddress.line2 || null,
      city: newAddress.city || '',
      provinceCode: newAddress.provinceCode || null,
      postalCode: newAddress.postalCode || null,
      countryCode: newAddress.countryCode || 'CA',
      geocode: {
        lat: parseFloat(newAddress.lat || '0'),
        lng: parseFloat(newAddress.lng || '0'),
      },
    };

    axios
      .post(`/api/party/${partyId}/address`, payload, {
        headers: {
          Authorization: `Bearer ${token}`,
          'X-Tenant-ID': tenantId,
          'Content-Type': 'application/json',
        },
      })
      .then(() => {
        message.success('Address added');
        fetchAddresses();
        setModalVisible(false);
        setNewAddress({
          kind: 'LEGAL',
          line1: '',
          line2: null,
          city: '',
          provinceCode: null,
          postalCode: null,
          countryCode: 'CA',
          effectiveTo: null,
          lat: '0',
          lng: '0',
        });
      })
      .catch((err) => {
        console.error('❌ Add failed:', err.response?.data || err.message);
        message.error(`Add failed: ${err.response?.data?.message || 'Unknown error'}`);
      });
  };

  const columns = [
    {
      title: 'Kind',
      dataIndex: 'kind',
      render: (text, record) =>
        editingKey === record.addressId ? (
          <Select
            value={record.kind}
            onChange={(value) =>
              setAddresses((prev) =>
                prev.map((item) =>
                  item.addressId === record.addressId ? { ...item, kind: value } : item
                )
              )
            }
          >
            <Option value="LEGAL">LEGAL</Option>
            <Option value="MAILING">MAILING</Option>
            <Option value="SERVICE">SERVICE</Option>
          </Select>
        ) : (
          text
        ),
    },
    {
      title: 'Line 1',
      dataIndex: 'line1',
      render: (text, record) =>
        editingKey === record.addressId ? (
          <Input
            value={record.line1}
            onChange={(e) => {
              const updatedValue = e.target.value;
              setAddresses((prev) =>
                prev.map((item) =>
                  item.addressId === record.addressId
                    ? { ...item, line1: updatedValue } // Update only the relevant field
                    : item
                )
              );
            }}
          />
        ) : (
          text
        ),
    },





    {
      title: 'City',
      dataIndex: 'city',
      render: (text, record) =>
        editingKey === record.addressId ? (
          <Input
            value={record.city}
            onChange={(e) =>
              setAddresses((prev) =>
                prev.map((item) =>
                  item.addressId === record.addressId ? { ...item, city: e.target.value } : item
                )
              )
            }
          />
        ) : (
          text
        ),
    },
    {
      title: 'Province',
      dataIndex: 'provinceCode',
      render: (text, record) =>
        editingKey === record.addressId ? (
          <Input
            value={record.provinceCode}
            onChange={(e) =>
              setAddresses((prev) =>
                prev.map((item) =>
                  item.addressId === record.addressId
                    ? { ...item, provinceCode: e.target.value }
                    : item
                )
              )
            }
          />
        ) : (
          text
        ),
    },
    {
      title: 'Postal Code',
      dataIndex: 'postalCode',
      render: (text, record) =>
        editingKey === record.addressId ? (
          <Input
            value={record.postalCode}
            onChange={(e) =>
              setAddresses((prev) =>
                prev.map((item) =>
                  item.addressId === record.addressId
                    ? { ...item, postalCode: e.target.value }
                    : item
                )
              )
            }
          />
        ) : (
          text
        ),
    },
    {
      title: 'Effective From',
      dataIndex: 'effectiveFrom',
      render: (text) => dayjs(text).format('YYYY-MM-DD'),
    },
    {
      title: 'Effective To',
      dataIndex: 'effectiveTo',
      render: (text, record) =>
        editingKey === record.addressId ? (
          <DatePicker
            value={record.effectiveTo ? dayjs(record.effectiveTo) : null}
            onChange={(date) =>
              setAddresses((prev) =>
                prev.map((item) =>
                  item.addressId === record.addressId
                    ? { ...item, effectiveTo: date?.format('YYYY-MM-DD') }
                    : item
                )
              )
            }
          />
        ) : (
          text || '—'
        ),
    },

{
  title: 'Actions',
  render: (_, record) =>
    editingKey === record.addressId ? (
      <>
        <Button type="link" onClick={() => handleSave(record)}>
          Save
        </Button>
        <Button type="link" onClick={() => setEditingKey('')}>
          Cancel
        </Button>
      </>
    ) : (
      <>
        <Button type="link" onClick={() => setEditingKey(record.addressId)}>
          Edit
        </Button>
        <Button
          type="link"
          danger
          onClick={(e) => {
            e.stopPropagation(); // Prevent event bubbling
            e.preventDefault(); // Prevent default behavior
            handleDelete(record);
          }}
        >
          Delete
        </Button>
      </>
    ),
},
  ];

  return (
    <>
      <Button type="primary" onClick={() => setModalVisible(true)} style={{ marginBottom: 16 }}>
        Add Address
      </Button>

      <Table
        rowKey="addressId"
        dataSource={addresses}
        columns={columns}
        pagination={false}
        loading={loading}
      />

      <Modal
        title="Add Address"
        open={modalVisible}
        onOk={handleAdd}
        onCancel={() => {
          setModalVisible(false);
          setNewAddress({
            kind: 'LEGAL',
            line1: '',
            line2: null,
            city: '',
            provinceCode: null,
            postalCode: null,
            countryCode: 'CA',
            effectiveTo: null,
            lat: '0',
            lng: '0',
          });
        }}
      >
        <div>
          <label>Kind</label>
          <Select
            placeholder="Select kind"
            value={newAddress.kind}
            onChange={(value) => setNewAddress({ ...newAddress, kind: value })}
            style={{ width: '100%', marginBottom: 16 }}
          >
            <Option value="LEGAL">LEGAL</Option>
            <Option value="MAILING">MAILING</Option>
            <Option value="SERVICE">SERVICE</Option>
          </Select>

          <label>Line 1</label>
          <Input
            value={newAddress.line1}
            onChange={(e) => setNewAddress({ ...newAddress, line1: e.target.value })}
            style={{ width: '100%', marginBottom: 16 }}
          />

          <label>Line 2</label>
          <Input
            value={newAddress.line2}
            onChange={(e) => setNewAddress({ ...newAddress, line2: e.target.value })}
            style={{ width: '100%', marginBottom: 16 }}
          />

          <label>City</label>
          <Input
            value={newAddress.city}
            onChange={(e) => setNewAddress({ ...newAddress, city: e.target.value })}
            style={{ width: '100%', marginBottom: 16 }}
          />

          <label>Province</label>
          <Input
            value={newAddress.provinceCode}
            onChange={(e) => setNewAddress({ ...newAddress, provinceCode: e.target.value })}
            style={{ width: '100%', marginBottom: 16 }}
          />

          <label>Postal Code</label>
          <Input
            value={newAddress.postalCode}
            onChange={(e) => setNewAddress({ ...newAddress, postalCode: e.target.value })}
            style={{ width: '100%', marginBottom: 16 }}
          />

          <label>Country</label>
          <Input
            value={newAddress.countryCode}
            onChange={(e) => setNewAddress({ ...newAddress, countryCode: e.target.value })}
            style={{ width: '100%', marginBottom: 16 }}
          />

          <label>Effective To</label>
          <DatePicker
            style={{ width: '100%', marginBottom: 16 }}
            value={newAddress.effectiveTo ? dayjs(newAddress.effectiveTo) : null}
            onChange={(date) =>
              setNewAddress({ ...newAddress, effectiveTo: date?.format('YYYY-MM-DD') })
            }
          />

          <label>Latitude</label>
          <Input
            value={newAddress.lat}
            onChange={(e) => setNewAddress({ ...newAddress, lat: e.target.value })}
            style={{ width: '100%', marginBottom: 16 }}
          />

          <label>Longitude</label>
          <Input
            value={newAddress.lng}
            onChange={(e) => setNewAddress({ ...newAddress, lng: e.target.value })}
            style={{ width: '100%', marginBottom: 16 }}
          />
        </div>
      </Modal>
    </>
  );
};

export default PartyAddressTab;