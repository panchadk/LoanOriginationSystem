import React from 'react';
import { usePartyService } from '../hooks/usePartyService';

const PartySummaryTab = ({ data }) => {
  const { toggleStatus } = usePartyService();

  if (!data) return <div>Loading party details...</div>;

  const handleToggle = async () => {
    const newStatus = data.status === 'ACTIVE' ? 'INACTIVE' : 'ACTIVE';
    await toggleStatus(data.partyId, newStatus);
    window.location.reload(); // or trigger a refresh via parent state
  };

  return (
    <div className="party-summary-tab">
      <h3>Party Summary</h3>
      <p><strong>ID:</strong> {data.partyId}</p>
      <p><strong>Kind:</strong> {data.kind}</p>
      <p><strong>Status:</strong> <span className={`status-badge ${data.status.toLowerCase()}`}>{data.status}</span></p>
      <p><strong>Created At:</strong> {new Date(data.createdAt).toLocaleString()}</p>
      <button onClick={handleToggle}>
        {data.status === 'ACTIVE' ? 'Deactivate' : 'Activate'}
      </button>
    </div>
  );
};

export default PartySummaryTab;
