import React from 'react';
import './css/OrganizationDetailsTab.css';

const OrganizationDetailsTab = ({ data, readOnly = true, onChange }) => {
  if (!data || data.kind !== 'ORGANIZATION') {
    return <div className="organization-details-tab empty">No organization details available.</div>;
  }

  const handleChange = (field) => (e) => {
    if (readOnly || !onChange) return;
    onChange({ ...data, [field]: e.target.value });
  };

  return (
    <div className="organization-details-tab">
      <h3>Organization Details</h3>

      <div className="form-group">
        <label>Legal Name:</label>
        {readOnly ? (
          <span>{data.legalName || '—'}</span>
        ) : (
          <input
            type="text"
            value={data.legalName || ''}
            onChange={handleChange('legalName')}
          />
        )}
      </div>

      <div className="form-group">
        <label>Business Type:</label>
        {readOnly ? (
          <span>{data.businessType || '—'}</span>
        ) : (
          <input
            type="text"
            value={data.businessType || ''}
            onChange={handleChange('businessType')}
          />
        )}
      </div>

      <div className="form-group">
        <label>BIN:</label>
        {readOnly ? (
          <span>{data.bin || '—'}</span>
        ) : (
          <input
            type="text"
            value={data.bin || ''}
            onChange={handleChange('bin')}
          />
        )}
      </div>

      <div className="form-group">
        <label>Registration Jurisdiction:</label>
        {readOnly ? (
          <span>{data.registrationJurisdiction || '—'}</span>
        ) : (
          <input
            type="text"
            value={data.registrationJurisdiction || ''}
            onChange={handleChange('registrationJurisdiction')}
          />
        )}
      </div>
    </div>
  );
};

export default OrganizationDetailsTab;
