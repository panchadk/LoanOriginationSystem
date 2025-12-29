import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './css/PartyBankAccountTab.css';

const PartyBankAccountTab = ({ partyId }) => {
  const [accounts, setAccounts] = useState([]);
  const [newAccount, setNewAccount] = useState({});
  const [dirtyRows, setDirtyRows] = useState(new Set());
  const [toast, setToast] = useState(null);
  const tenantId = localStorage.getItem('tenantId');

  useEffect(() => {
    if (partyId && tenantId) {
      axios
        .get(`/api/party/${partyId}/bank-account`, {
          headers: { 'X-Tenant-ID': tenantId },
        })
        .then((res) => setAccounts(res.data))
        .catch(() => showToast('Failed to load bank accounts', 'error'));
    }
  }, [partyId, tenantId]);

  const showToast = (message, type = 'info') => {
    setToast({ message, type });
    setTimeout(() => setToast(null), 3000);
  };

  const handleChange = (index, field, value) => {
    const updated = [...accounts];
    updated[index][field] = value;
    setAccounts(updated);
    const updatedDirty = new Set(dirtyRows);
    updatedDirty.add(index);
    setDirtyRows(updatedDirty);
  };

  const handleSave = (index) => {
    const acc = accounts[index];
    if (!acc.bankName || !acc.accountType || !acc.accountMaskLast4) {
      showToast('Bank name, account type, and last 4 are required', 'warning');
      return;
    }

    axios
      .put(`/api/party/${partyId}/bank-account/${acc.bankAccountId}`, acc, {
        headers: { 'X-Tenant-ID': tenantId },
      })
      .then(() => {
        showToast('Bank account saved', 'success');
        const updatedDirty = new Set(dirtyRows);
        updatedDirty.delete(index);
        setDirtyRows(updatedDirty);
      })
      .catch(() => showToast('Save failed', 'error'));
  };

  const handleDelete = (bankAccountId) => {
    axios
      .delete(`/api/party/${partyId}/bank-account/${bankAccountId}`, {
        headers: { 'X-Tenant-ID': tenantId },
      })
      .then(() => {
        setAccounts((prev) => prev.filter((a) => a.bankAccountId !== bankAccountId));
        showToast('Bank account deleted', 'success');
      })
      .catch(() => showToast('Delete failed', 'error'));
  };

  const handleNewChange = (field, value) => {
    setNewAccount((prev) => ({ ...prev, [field]: value }));
  };

  const handleAdd = () => {
    if (!newAccount.bankName || !newAccount.accountType || !newAccount.accountMaskLast4) {
      showToast('Bank name, account type, and last 4 are required', 'warning');
      return;
    }

    axios
      .post(`/api/party/${partyId}/bank-account`, newAccount, {
        headers: { 'X-Tenant-ID': tenantId },
      })
      .then((res) => {
        setAccounts((prev) => [...prev, res.data]);
        setNewAccount({});
        showToast('Bank account added', 'success');
      })
      .catch(() => showToast('Add failed', 'error'));
  };

  return (
    <div className="party-bank-account-tab">
      <h3>Bank Accounts</h3>
      {toast && <div className={`toast ${toast.type}`}>{toast.message}</div>}
      <table>
        <thead>
          <tr>
            <th>Bank Name</th>
            <th>Type</th>
            <th>Last 4</th>
            <th>Default</th>
            <th>Verified</th>
            <th>Consent</th>
            <th>Limit</th>
            <th>Notes</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {accounts.map((acc, index) => (
            <tr key={acc.bankAccountId} className={dirtyRows.has(index) ? 'dirty' : ''}>
              <td><input value={acc.bankName || ''} onChange={(e) => handleChange(index, 'bankName', e.target.value)} /></td>
              <td><input value={acc.accountType || ''} onChange={(e) => handleChange(index, 'accountType', e.target.value)} /></td>
              <td><input value={acc.accountMaskLast4 || ''} onChange={(e) => handleChange(index, 'accountMaskLast4', e.target.value)} /></td>
              <td><input type="checkbox" checked={acc.isDefault || false} onChange={(e) => handleChange(index, 'isDefault', e.target.checked)} /></td>
              <td><input type="checkbox" checked={acc.verificationStatus === 'VERIFIED'} onChange={(e) => handleChange(index, 'verificationStatus', e.target.checked ? 'VERIFIED' : 'UNVERIFIED')} /></td>
              <td><input type="checkbox" checked={acc.consentToDebit || false} onChange={(e) => handleChange(index, 'consentToDebit', e.target.checked)} /></td>
              <td><input type="number" value={acc.debitDailyLimit || ''} onChange={(e) => handleChange(index, 'debitDailyLimit', e.target.value)} /></td>
              <td><input value={acc.notes || ''} onChange={(e) => handleChange(index, 'notes', e.target.value)} /></td>
              <td>
                <button onClick={() => handleSave(index)}>Save</button>
                <button onClick={() => handleDelete(acc.bankAccountId)}>Delete</button>
              </td>
            </tr>
          ))}

          {/* New account row */}
          <tr>
            <td><input value={newAccount.bankName || ''} onChange={(e) => handleNewChange('bankName', e.target.value)} placeholder="Bank name" /></td>
            <td><input value={newAccount.accountType || ''} onChange={(e) => handleNewChange('accountType', e.target.value)} placeholder="Type" /></td>
            <td><input value={newAccount.accountMaskLast4 || ''} onChange={(e) => handleNewChange('accountMaskLast4', e.target.value)} placeholder="Last 4" /></td>
            <td><input type="checkbox" checked={newAccount.isDefault || false} onChange={(e) => handleNewChange('isDefault', e.target.checked)} /></td>
            <td><input type="checkbox" checked={newAccount.verificationStatus === 'VERIFIED'} onChange={(e) => handleNewChange('verificationStatus', e.target.checked ? 'VERIFIED' : 'UNVERIFIED')} /></td>
            <td><input type="checkbox" checked={newAccount.consentToDebit || false} onChange={(e) => handleNewChange('consentToDebit', e.target.checked)} /></td>
            <td><input type="number" value={newAccount.debitDailyLimit || ''} onChange={(e) => handleNewChange('debitDailyLimit', e.target.value)} /></td>
            <td><input value={newAccount.notes || ''} onChange={(e) => handleNewChange('notes', e.target.value)} /></td>
            <td><button onClick={handleAdd}>Add</button></td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default PartyBankAccountTab;
