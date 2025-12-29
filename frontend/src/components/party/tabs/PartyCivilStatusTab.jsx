import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './css/PartyCivicStatusTab.css';

const PartyCivicStatusTab = ({ partyId }) => {
  const [statuses, setStatuses] = useState([]);
  const [newStatus, setNewStatus] = useState({});
  const [dirtyRows, setDirtyRows] = useState(new Set());
  const [toast, setToast] = useState(null);
  const tenantId = localStorage.getItem('tenantId');

  useEffect(() => {
    if (partyId && tenantId) {
      axios
        .get(`/api/party/${partyId}/civic-status`, {
          headers: { 'X-Tenant-ID': tenantId },
        })
        .then((res) => setStatuses(res.data))
        .catch(() => showToast('Failed to load civic statuses', 'error'));
    }
  }, [partyId, tenantId]);

  const showToast = (message, type = 'info') => {
    setToast({ message, type });
    setTimeout(() => setToast(null), 3000);
  };

  const handleChange = (index, field, value) => {
    const updated = [...statuses];
    updated[index][field] = value;
    setStatuses(updated);
    const updatedDirty = new Set(dirtyRows);
    updatedDirty.add(index);
    setDirtyRows(updatedDirty);
  };

  const handleSave = (index) => {
    const status = statuses[index];
    if (!status.status || !status.effectiveFrom) {
      showToast('Status and effective from date are required', 'warning');
      return;
    }

    axios
      .put(`/api/party/${partyId}/civic-status/${status.civicStatusId}`, status, {
        headers: { 'X-Tenant-ID': tenantId },
      })
      .then(() => {
        showToast('Civic status saved', 'success');
        const updatedDirty = new Set(dirtyRows);
        updatedDirty.delete(index);
        setDirtyRows(updatedDirty);
      })
      .catch(() => showToast('Save failed', 'error'));
  };

  const handleDelete = (civicStatusId) => {
    axios
      .delete(`/api/party/${partyId}/civic-status/${civicStatusId}`, {
        headers: { 'X-Tenant-ID': tenantId },
      })
      .then(() => {
        setStatuses((prev) => prev.filter((s) => s.civicStatusId !== civicStatusId));
        showToast('Civic status deleted', 'success');
      })
      .catch(() => showToast('Delete failed', 'error'));
  };

  const handleNewChange = (field, value) => {
    setNewStatus((prev) => ({ ...prev, [field]: value }));
  };

  const handleAdd = () => {
    if (!newStatus.status || !newStatus.effectiveFrom) {
      showToast('Status and effective from date are required', 'warning');
      return;
    }

    axios
      .post(`/api/party/${partyId}/civic-status`, newStatus, {
        headers: { 'X-Tenant-ID': tenantId },
      })
      .then((res) => {
        setStatuses((prev) => [...prev, res.data]);
        setNewStatus({});
        showToast('Civic status added', 'success');
      })
      .catch(() => showToast('Add failed', 'error'));
  };

  return (
    <div className="party-civic-status-tab">
      <h3>Civic Status</h3>
      {toast && <div className={`toast ${toast.type}`}>{toast.message}</div>}
      <table>
        <thead>
          <tr>
            <th>Status</th>
            <th>Effective From</th>
            <th>Effective To</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {statuses.map((s, index) => (
            <tr key={s.civicStatusId} className={dirtyRows.has(index) ? 'dirty' : ''}>
              <td><input value={s.status || ''} onChange={(e) => handleChange(index, 'status', e.target.value)} /></td>
              <td><input type="date" value={s.effectiveFrom || ''} onChange={(e) => handleChange(index, 'effectiveFrom', e.target.value)} /></td>
              <td><input type="date" value={s.effectiveTo || ''} onChange={(e) => handleChange(index, 'effectiveTo', e.target.value)} /></td>
              <td>
                <button onClick={() => handleSave(index)}>Save</button>
                <button onClick={() => handleDelete(s.civicStatusId)}>Delete</button>
              </td>
            </tr>
          ))}

          {/* New status row */}
          <tr>
            <td><input value={newStatus.status || ''} onChange={(e) => handleNewChange('status', e.target.value)} placeholder="Status" /></td>
            <td><input type="date" value={newStatus.effectiveFrom || ''} onChange={(e) => handleNewChange('effectiveFrom', e.target.value)} /></td>
            <td><input type="date" value={newStatus.effectiveTo || ''} onChange={(e) => handleNewChange('effectiveTo', e.target.value)} /></td>
            <td><button onClick={handleAdd}>Add</button></td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default PartyCivicStatusTab;
