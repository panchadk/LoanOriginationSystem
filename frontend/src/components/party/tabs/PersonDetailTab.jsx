import React from 'react';
import './css/PersonDetailsTab.css';

const PersonDetailsTab = ({ data, readOnly = true, onChange }) => {
  if (!data || !data.givenName) {
    return <div className="person-details-tab empty">No person details available.</div>;
  }

  const handleChange = (field) => (e) => {
    if (readOnly || !onChange) return;
    onChange({ ...data, [field]: e.target.value });
  };

  return (
    <div className="person-details-tab">
      <h3>Person Details</h3>

      <div className="form-group">
        <label>Given Name:</label>
        {readOnly ? (
          <span>{data.givenName || '—'}</span>
        ) : (
          <input
            type="text"
            value={data.givenName || ''}
            onChange={handleChange('givenName')}
          />
        )}
      </div>

      <div className="form-group">
        <label>Middle Name:</label>
        {readOnly ? (
          <span>{data.middleName || '—'}</span>
        ) : (
          <input
            type="text"
            value={data.middleName || ''}
            onChange={handleChange('middleName')}
          />
        )}
      </div>

      <div className="form-group">
        <label>Family Name:</label>
        {readOnly ? (
          <span>{data.familyName || '—'}</span>
        ) : (
          <input
            type="text"
            value={data.familyName || ''}
            onChange={handleChange('familyName')}
          />
        )}
      </div>

      <div className="form-group">
        <label>Date of Birth:</label>
        {readOnly ? (
          <span>{data.dob || '—'}</span>
        ) : (
          <input
            type="date"
            value={data.dob || ''}
            onChange={handleChange('dob')}
          />
        )}
      </div>

      <div className="form-group">
        <label>Residency Status:</label>
        {readOnly ? (
          <span>{data.residencyStatus || '—'}</span>
        ) : (
          <input
            type="text"
            value={data.residencyStatus || ''}
            onChange={handleChange('residencyStatus')}
          />
        )}
      </div>
    </div>
  );
};

export default PersonDetailsTab;
