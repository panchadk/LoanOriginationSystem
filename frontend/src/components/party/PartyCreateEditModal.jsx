import React, { useState, useEffect } from 'react';
import './css/PartyCreateEditModal.css';

const PartyCreateEditModal = ({ party, tenantId, onClose }) => {
  const isEdit = !!party;

  const [form, setForm] = useState({
    kind: 'PERSON',
    status: 'ACTIVE',
    givenName: '',
    middleName: '',
    familyName: '',
    dob: '',
    residencyStatus: '',
    legalName: '',
    registrationJurisdiction: '',
    businessType: '',
    bin: '',
  });

  useEffect(() => {
    if (isEdit && party) {
      setForm((prevForm) => ({ ...prevForm, ...party }));
    }
  }, [party, isEdit]);

  const handleChange = (field) => (e) => {
    setForm((f) => ({ ...f, [field]: e.target.value }));
  };

  const handleSubmit = async () => {
    try {
      const url = isEdit ? `/api/party/${party.partyId}` : '/api/party';
      const method = isEdit ? 'PUT' : 'POST';

      const payload =
        form.kind === 'PERSON'
          ? {
              kind: form.kind,
              status: form.status,
              givenName: form.givenName,
              middleName: form.middleName,
              familyName: form.familyName,
              dob: form.dob,
              residencyStatus: form.residencyStatus,
            }
          : {
              kind: form.kind,
              status: form.status,
              legalName: form.legalName,
              registrationJurisdiction: form.registrationJurisdiction,
              businessType: form.businessType,
              bin: form.bin,
            };

      const res = await fetch(url, {
        method,
        headers: {
          'Content-Type': 'application/json',
          'X-Tenant-ID': tenantId,
        },
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        const error = await res.json();
        throw new Error(error.error || 'Failed to submit party');
      }

      onClose();
    } catch (err) {
      console.error('Error submitting party:', err.message);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <h2>{isEdit ? 'Edit Party' : 'Create Party'}</h2>

        <div className="form-group">
          <label>Kind:</label>
          <select value={form.kind} onChange={handleChange('kind')}>
            <option value="PERSON">Person</option>
            <option value="ORGANIZATION">Organization</option>
          </select>
        </div>

        <div className="form-group">
          <label>Status:</label>
          <select value={form.status} onChange={handleChange('status')}>
            <option value="ACTIVE">Active</option>
            <option value="INACTIVE">Inactive</option>
          </select>
        </div>

        {form.kind === 'PERSON' && (
          <>
            <div className="form-group">
              <label>Given Name:</label>
              <input type="text" value={form.givenName} onChange={handleChange('givenName')} />
            </div>
            <div className="form-group">
              <label>Middle Name:</label>
              <input type="text" value={form.middleName} onChange={handleChange('middleName')} />
            </div>
            <div className="form-group">
              <label>Family Name:</label>
              <input type="text" value={form.familyName} onChange={handleChange('familyName')} />
            </div>
            <div className="form-group">
              <label>Date of Birth:</label>
              <input type="date" value={form.dob} onChange={handleChange('dob')} />
            </div>
            <div className="form-group">
              <label>Residency Status:</label>
              <input
                type="text"
                value={form.residencyStatus}
                onChange={handleChange('residencyStatus')}
              />
            </div>
          </>
        )}

        {form.kind === 'ORGANIZATION' && (
          <>
            <div className="form-group">
              <label>Legal Name:</label>
              <input type="text" value={form.legalName} onChange={handleChange('legalName')} />
            </div>
            <div className="form-group">
              <label>Registration Jurisdiction:</label>
              <input
                type="text"
                value={form.registrationJurisdiction}
                onChange={handleChange('registrationJurisdiction')}
              />
            </div>
            <div className="form-group">
              <label>Business Type:</label>
              <input
                type="text"
                value={form.businessType}
                onChange={handleChange('businessType')}
              />
            </div>
            <div className="form-group">
              <label>BIN:</label>
              <input type="text" value={form.bin} onChange={handleChange('bin')} />
            </div>
          </>
        )}

        <div className="modal-actions">
          <button className="btn btn-primary" onClick={handleSubmit}>
            {isEdit ? 'Update' : 'Create'}
          </button>
          <button className="btn btn-secondary" onClick={onClose}>
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
};

export default PartyCreateEditModal;
