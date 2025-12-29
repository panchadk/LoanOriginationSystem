import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './css/PartyContactTab.css';

const allowedTypes = ['ANY', 'EMAIL', 'MOBILE', 'PHONE', 'FAX', 'PORTAL'];

const PartyContactTab = ({ partyId }) => {
  const [contacts, setContacts] = useState([]);
  const [newContact, setNewContact] = useState({
    type: '',
    value: '',
    preferred: false,
    effectiveFrom: '',
    effectiveTo: ''
  });
  const [dirtyRows, setDirtyRows] = useState(new Set());
  const [toast, setToast] = useState(null);
  const [confirmDeleteId, setConfirmDeleteId] = useState(null);

  const tenantId = localStorage.getItem('tenantId');
  const token = localStorage.getItem('token');

  const fetchContacts = () => {
    if (!partyId || !tenantId || !token) return;

    axios
      .get(`/api/party/${partyId}/contact`, {
        headers: {
          'X-Tenant-ID': tenantId,
          'Authorization': `Bearer ${token}`
        }
      })
      .then((res) => {
        const flatContacts = res.data.map((item) => ({
          contactId: item.contactId,
          type: item.type,
          value: item.value || '',
          preferred: item.preferred || false,
          effectiveFrom: item.effectiveFrom || '',
          effectiveTo: item.effectiveTo || ''
        }));
        setContacts(flatContacts);
        setDirtyRows(new Set());
      })
      .catch((err) => {
        console.error('❌ Failed to load contacts:', err);
        showToast('Failed to load contacts', 'error');
      });
  };

  useEffect(() => {
    fetchContacts();
  }, [partyId, tenantId]);

  const showToast = (message, type = 'info') => {
    setToast({ message, type });
    setTimeout(() => setToast(null), 3000);
  };

  const handleChange = (index, field, value) => {
    const updated = [...contacts];
    updated[index][field] = value;
    setContacts(updated);

    const updatedDirty = new Set(dirtyRows);
    updatedDirty.add(index);
    setDirtyRows(updatedDirty);
  };

  const handleSave = (index) => {
    const contact = contacts[index];
    if (!contact.type || !contact.value || !contact.effectiveFrom || !contact.contactId) {
      showToast('Type, Value, Effective From, and Contact ID are required', 'warning');
      return;
    }

    axios
      .put(`/api/party/${partyId}/contact/${contact.contactId}`, contact, {
        headers: {
          'X-Tenant-ID': tenantId,
          'Authorization': `Bearer ${token}`
        }
      })
      .then(() => {
        showToast('Contact saved', 'success');
        fetchContacts(); // ✅ auto-refresh
      })
      .catch((err) => {
        console.error(err);
        showToast('Save failed', 'error');
      });
  };

  const confirmDelete = (contactId) => {
    setConfirmDeleteId(contactId);
  };

  const cancelDelete = () => {
    setConfirmDeleteId(null);
  };

 const handleDeleteConfirmed = () => {
   const contact = contacts.find(c => c.contactId === confirmDeleteId);
   if (!contact) {
     showToast('Contact not found', 'error');
     return;
   }

   axios
     .delete(`/api/party/${partyId}/contact/${confirmDeleteId}/${contact.effectiveFrom}`, {
       headers: {
         'X-Tenant-ID': tenantId,
         'Authorization': `Bearer ${token}`
       }
     })
     .then(() => {
       showToast('Contact deleted', 'success');
       fetchContacts(); // ✅ refresh list
       setConfirmDeleteId(null);
     })
     .catch((err) => {
       console.error(err);
       showToast('Delete failed', 'error');
       setConfirmDeleteId(null);
     });
 };

  const handleNewChange = (field, value) => {
    setNewContact((prev) => ({ ...prev, [field]: value }));
  };

  const handleAdd = async () => {
    if (!newContact.type || !newContact.value || !newContact.effectiveFrom) {
      showToast('Type, Value, and Effective From are required', 'warning');
      return;
    }

    const normalizedType = newContact.type.toUpperCase();

    try {
      const contactPointRes = await axios.post(
        `/api/contact-point`,
        {
          type: normalizedType,
          value: newContact.value,
          verified: false,
          createdAt: new Date().toISOString()
        },
        {
          headers: {
            'X-Tenant-ID': tenantId,
            'Authorization': `Bearer ${token}`
          }
        }
      );

      const contactId = contactPointRes.data.contactId;

      await axios.post(
        `/api/party/${partyId}/contact`,
        {
          contactId,
          type: newContact.type,
          preferred: newContact.preferred,
          effectiveFrom: newContact.effectiveFrom,
          effectiveTo: newContact.effectiveTo || null
        },
        {
          headers: {
            'X-Tenant-ID': tenantId,
            'Authorization': `Bearer ${token}`
          }
        }
      );

      showToast('Contact added', 'success');
      fetchContacts(); // ✅ auto-refresh

      setNewContact({
        type: '',
        value: '',
        preferred: false,
        effectiveFrom: '',
        effectiveTo: ''
      });
    } catch (err) {
      console.error(err);
      showToast('Add failed', 'error');
    }
  };

  return (
    <div className="party-contact-tab">
      <h3>Party Contacts</h3>
      {toast && <div className={`toast ${toast.type}`}>{toast.message}</div>}

      {confirmDeleteId && (
        <div className="confirm-modal">
          <div className="confirm-box">
            <p>Are you sure you want to delete this contact?</p>
            <button onClick={handleDeleteConfirmed}>Yes, Delete</button>
            <button onClick={cancelDelete}>Cancel</button>
          </div>
        </div>
      )}

      <table>
        <thead>
          <tr>
            <th>Type</th>
            <th>Value</th>
            <th>Preferred</th>
            <th>Effective From</th>
            <th>Effective To</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {contacts.map((contact, index) => (
            <tr key={contact.contactId} className={dirtyRows.has(index) ? 'dirty' : ''}>
              <td>
                <select
                  value={contact.type || ''}
                  onChange={(e) => handleChange(index, 'type', e.target.value)}
                >
                  <option value="">Select type</option>
                  {allowedTypes.map((type) => (
                    <option key={type} value={type}>{type}</option>
                  ))}
                </select>
              </td>
              <td>
                <input
                  value={contact.value || ''}
                  onChange={(e) => handleChange(index, 'value', e.target.value)}
                />
              </td>
              <td>
                <select
                  value={contact.preferred ? 'true' : 'false'}
                  onChange={(e) => handleChange(index, 'preferred', e.target.value === 'true')}
                >
                  <option value="false">No</option>
                  <option value="true">Yes</option>
                </select>
              </td>
              <td>
                <input
                  type="date"
                  value={contact.effectiveFrom || ''}
                  onChange={(e) => handleChange(index, 'effectiveFrom', e.target.value)}
                />
              </td>
              <td>
                <input
                  type="date"
                  value={contact.effectiveTo || ''}
                  onChange={(e) => handleChange(index, 'effectiveTo', e.target.value)}
                />
              </td>
              <td>
                <button onClick={() => handleSave(index)}>Save</button>
                <button onClick={() => confirmDelete(contact.contactId)}>Delete</button>
              </td>
            </tr>
          ))}

          <tr>
            <td>
              <select
                value={newContact.type}
                onChange={(e) => handleNewChange('type', e.target.value)}
              >
                <option value="">Select type</option>
                {allowedTypes.map((type) => (
                  <option key={type} value={type}>{type}</option>
                ))}
              </select>
            </td>
            <td>
              <input
                value={newContact.value}
                onChange={(e) => handleNewChange('value', e.target.value)}
                placeholder="New value"
              />
            </td>
            <td>
              <select
                value={newContact.preferred ? 'true' : 'false'}
                onChange={(e) => handleNewChange('preferred', e.target.value === 'true')}>
                <option value="false">No</option>
                                <option value="true">Yes</option>
                              </select>
                            </td>
                            <td>
                              <input
                                type="date"
                                value={newContact.effectiveFrom}
                                onChange={(e) => handleNewChange('effectiveFrom', e.target.value)}
                              />
                            </td>
                            <td>
                              <input
                                type="date"
                                value={newContact.effectiveTo}
                                onChange={(e) => handleNewChange('effectiveTo', e.target.value)}
                              />
                            </td>
                            <td>
                              <button onClick={handleAdd}>Add</button>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  );
                };

                export default PartyContactTab;
