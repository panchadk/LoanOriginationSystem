import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './css/PartyIdentifierTab.css';

const PartyIdentifierTab = ({ partyId }) => {
  const [identifiers, setIdentifiers] = useState([]);
  const [newIdentifier, setNewIdentifier] = useState({
    typeCode: '',
    valueCiphertext: '',
    last4: '',
    issuer: '',
    validFrom: '',
    validTo: ''
  });
  const [dirtyRows, setDirtyRows] = useState(new Set());
  const [toast, setToast] = useState(null);

  const tenantId = localStorage.getItem('tenantId');

  useEffect(() => {
    if (partyId && tenantId) {
      axios
        .get(`/api/party/${partyId}/identifier`, {
          headers: { 'X-Tenant-ID': tenantId },
        })
        .then((res) => setIdentifiers(res.data))
        .catch(() => showToast('Failed to load identifiers', 'error'));
    }
  }, [partyId, tenantId]);

  const showToast = (message, type = 'info') => {
    setToast({ message, type });
    setTimeout(() => setToast(null), 3000);
  };

  const handleChange = (index, field, value) => {
    const updated = [...identifiers];
    updated[index][field] = value;
    setIdentifiers(updated);

    const updatedDirty = new Set(dirtyRows);
    updatedDirty.add(index);
    setDirtyRows(updatedDirty);
  };

  const handleSave = (index) => {
    const id = identifiers[index];
    if (!id.typeCode || !id.valueCiphertext || !id.validFrom) {
      showToast('Type, Value, and Valid From are required', 'warning');
      return;
    }

    axios
      .put(`/api/party/${partyId}/identifier/${id.identifierId}`, id, {
        headers: { 'X-Tenant-ID': tenantId },
      })
      .then(() => {
        showToast('Identifier saved', 'success');
        const updatedDirty = new Set(dirtyRows);
        updatedDirty.delete(index);
        setDirtyRows(updatedDirty);
      })
      .catch(() => showToast('Save failed', 'error'));
  };

  const handleDelete = (identifierId) => {
    axios
      .delete(`/api/party/${partyId}/identifier/${identifierId}`, {
        headers: { 'X-Tenant-ID': tenantId },
      })
      .then(() => {
        setIdentifiers((prev) => prev.filter((i) => i.identifierId !== identifierId));
        showToast('Identifier deleted', 'success');
      })
      .catch(() => showToast('Delete failed', 'error'));
  };

  const handleNewChange = (field, value) => {
    setNewIdentifier((prev) => ({ ...prev, [field]: value }));
  };

  const handleAdd = () => {
    if (!newIdentifier.typeCode || !newIdentifier.valueCiphertext || !newIdentifier.validFrom) {
      showToast('Type, Value, and Valid From are required', 'warning');
      return;
    }

    axios
      .post(`/api/party/${partyId}/identifier`, newIdentifier, {
        headers: { 'X-Tenant-ID': tenantId },
      })
      .then((res) => {
        setIdentifiers((prev) => [...prev, res.data]);
        setNewIdentifier({
          typeCode: '',
          valueCiphertext: '',
          last4: '',
          issuer: '',
          validFrom: '',
          validTo: ''
        });
        showToast('Identifier added', 'success');
      })
      .catch(() => showToast('Add failed', 'error'));
  };

  return (
    <div className="party-identifier-tab">
      <h3>Party Identifiers</h3>
      {toast && <div className={`toast ${toast.type}`}>{toast.message}</div>}
      <table>
        <thead>
          <tr>
            <th>Type</th>
            <th>Value</th>
            <th>Last 4</th>
            <th>Issuer</th>
            <th>Valid From</th>
            <th>Valid To</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {identifiers.map((id, index) => (
            <tr key={id.identifierId} className={dirtyRows.has(index) ? 'dirty' : ''}>
              <td>
                <input
                  value={id.typeCode || ''}
                  onChange={(e) => handleChange(index, 'typeCode', e.target.value)}
                />
              </td>
              <td>
                <input
                  value={id.valueCiphertext || ''}
                  onChange={(e) => handleChange(index, 'valueCiphertext', e.target.value)}
                />
              </td>
              <td>
                <input
                  value={id.last4 || ''}
                  onChange={(e) => handleChange(index, 'last4', e.target.value)}
                />
              </td>
              <td>
                <input
                  value={id.issuer || ''}
                  onChange={(e) => handleChange(index, 'issuer', e.target.value)}
                />
              </td>
              <td>
                <input
                  type="date"
                  value={id.validFrom || ''}
                  onChange={(e) => handleChange(index, 'validFrom', e.target.value)}
                />
              </td>
              <td>
                <input
                  type="date"
                  value={id.validTo || ''}
                  onChange={(e) => handleChange(index, 'validTo', e.target.value)}
                />
              </td>
              <td>
                <button onClick={() => handleSave(index)}>Save</button>
                <button onClick={() => handleDelete(id.identifierId)}>Delete</button>
              </td>
            </tr>
          ))}

          {/* New identifier row */}
          <tr>
            <td>
              <input
                value={newIdentifier.typeCode}
                onChange={(e) => handleNewChange('typeCode', e.target.value)}
                placeholder="New type"
              />
            </td>
            <td>
              <input
                value={newIdentifier.valueCiphertext}
                onChange={(e) => handleNewChange('valueCiphertext', e.target.value)}
                placeholder="New value"
              />
            </td>
            <td>
              <input
                value={newIdentifier.last4}
                onChange={(e) => handleNewChange('last4', e.target.value)}
                placeholder="Last 4"
              />
            </td>
            <td>
              <input
                value={newIdentifier.issuer}
                onChange={(e) => handleNewChange('issuer', e.target.value)}
                placeholder="Issuer"
              />
            </td>
            <td>
              <input
                type="date"
                value={newIdentifier.validFrom}
                onChange={(e) => handleNewChange('validFrom', e.target.value)}
              />
            </td>
            <td>
              <input
                type="date"
                value={newIdentifier.validTo}
                onChange={(e) => handleNewChange('validTo', e.target.value)}
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

export default PartyIdentifierTab;
