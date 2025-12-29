import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './css/PartyRelationshipTab.css';

const PartyRelationshipTab = ({ partyId }) => {
  const [relationships, setRelationships] = useState([]);
  const [newRel, setNewRel] = useState({});
  const [dirtyRows, setDirtyRows] = useState(new Set());
  const [toast, setToast] = useState(null);
  const tenantId = localStorage.getItem('tenantId');

  useEffect(() => {
    if (partyId && tenantId) {
      axios
        .get(`/api/party/${partyId}/relationship`, {
          headers: { 'X-Tenant-ID': tenantId },
        })
        .then((res) => setRelationships(res.data))
        .catch(() => showToast('Failed to load relationships', 'error'));
    }
  }, [partyId, tenantId]);

  const showToast = (message, type = 'info') => {
    setToast({ message, type });
    setTimeout(() => setToast(null), 3000);
  };

  const handleChange = (index, field, value) => {
    const updated = [...relationships];
    updated[index][field] = value;
    setRelationships(updated);
    const updatedDirty = new Set(dirtyRows);
    updatedDirty.add(index);
    setDirtyRows(updatedDirty);
  };

  const handleSave = (index) => {
    const rel = relationships[index];
    if (!rel.dstPartyId || !rel.type || !rel.effectiveFrom) {
      showToast('Destination party, type, and effective from are required', 'warning');
      return;
    }

    axios
      .put(`/api/party/${partyId}/relationship/${rel.relationshipId}`, rel, {
        headers: { 'X-Tenant-ID': tenantId },
      })
      .then(() => {
        showToast('Relationship saved', 'success');
        const updatedDirty = new Set(dirtyRows);
        updatedDirty.delete(index);
        setDirtyRows(updatedDirty);
      })
      .catch(() => showToast('Save failed', 'error'));
  };

  const handleDelete = (relationshipId) => {
    axios
      .delete(`/api/party/${partyId}/relationship/${relationshipId}`, {
        headers: { 'X-Tenant-ID': tenantId },
      })
      .then(() => {
        setRelationships((prev) => prev.filter((r) => r.relationshipId !== relationshipId));
        showToast('Relationship deleted', 'success');
      })
      .catch(() => showToast('Delete failed', 'error'));
  };

  const handleNewChange = (field, value) => {
    setNewRel((prev) => ({ ...prev, [field]: value }));
  };

  const handleAdd = () => {
    if (!newRel.dstPartyId || !newRel.type || !newRel.effectiveFrom) {
      showToast('Destination party, type, and effective from are required', 'warning');
      return;
    }

    axios
      .post(`/api/party/${partyId}/relationship`, newRel, {
        headers: { 'X-Tenant-ID': tenantId },
      })
      .then((res) => {
        setRelationships((prev) => [...prev, res.data]);
        setNewRel({});
        showToast('Relationship added', 'success');
      })
      .catch(() => showToast('Add failed', 'error'));
  };

  return (
    <div className="party-relationship-tab">
      <h3>Party Relationships</h3>
      {toast && <div className={`toast ${toast.type}`}>{toast.message}</div>}
      <table>
        <thead>
          <tr>
            <th>Destination Party ID</th>
            <th>Type</th>
            <th>Effective From</th>
            <th>Effective To</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {relationships.map((r, index) => (
            <tr key={r.relationshipId} className={dirtyRows.has(index) ? 'dirty' : ''}>
              <td><input value={r.dstPartyId || ''} onChange={(e) => handleChange(index, 'dstPartyId', e.target.value)} /></td>
              <td><input value={r.type || ''} onChange={(e) => handleChange(index, 'type', e.target.value)} /></td>
              <td><input type="date" value={r.effectiveFrom || ''} onChange={(e) => handleChange(index, 'effectiveFrom', e.target.value)} /></td>
              <td><input type="date" value={r.effectiveTo || ''} onChange={(e) => handleChange(index, 'effectiveTo', e.target.value)} /></td>
              <td>
                <button onClick={() => handleSave(index)}>Save</button>
                <button onClick={() => handleDelete(r.relationshipId)}>Delete</button>
              </td>
            </tr>
          ))}

          {/* New relationship row */}
          <tr>
            <td><input value={newRel.dstPartyId || ''} onChange={(e) => handleNewChange('dstPartyId', e.target.value)} placeholder="Destination Party ID" /></td>
            <td><input value={newRel.type || ''} onChange={(e) => handleNewChange('type', e.target.value)} placeholder="Type" /></td>
            <td><input type="date" value={newRel.effectiveFrom || ''} onChange={(e) => handleNewChange('effectiveFrom', e.target.value)} /></td>
            <td><input type="date" value={newRel.effectiveTo || ''} onChange={(e) => handleNewChange('effectiveTo', e.target.value)} /></td>
            <td><button onClick={handleAdd}>Add</button></td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default PartyRelationshipTab;
