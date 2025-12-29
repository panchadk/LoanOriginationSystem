import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './css/PartyAuditTab.css';

const PartyAuditTab = ({ partyId }) => {
  const [events, setEvents] = useState([]);
  const [toast, setToast] = useState(null);
  const tenantId = localStorage.getItem('tenantId');

  useEffect(() => {
    if (partyId && tenantId) {
      axios
        .get(`/api/party/${partyId}/audit-trail`, {
          headers: { 'X-Tenant-ID': tenantId },
        })
        .then((res) => setEvents(res.data))
        .catch(() => showToast('Failed to load audit events', 'error'));
    }
  }, [partyId, tenantId]);

  const showToast = (message, type = 'info') => {
    setToast({ message, type });
    setTimeout(() => setToast(null), 3000);
  };

  return (
    <div className="party-audit-tab">
      <h3>Audit History</h3>
      {toast && <div className={`toast ${toast.type}`}>{toast.message}</div>}
      {events.length === 0 ? (
        <p>No audit events found.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Event Type</th>
              <th>Created By</th>
              <th>Timestamp</th>
              <th>Details</th>
            </tr>
          </thead>
          <tbody>
            {events.map((e) => (
              <tr key={e.auditEventId}>
                <td>{e.eventType}</td>
                <td>{e.createdBy}</td>
                <td>{new Date(e.occurredAt).toLocaleString()}</td>
                <td>
                  <pre>{JSON.stringify(e.details, null, 2)}</pre>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default PartyAuditTab;
