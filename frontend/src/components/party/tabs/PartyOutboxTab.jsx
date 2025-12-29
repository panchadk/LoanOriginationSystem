import React, { useEffect, useState } from 'react';
import { usePartyService } from '../hooks/usePartyService';

const PartyOutboxTab = ({ partyId }) => {
  const [outboxItems, setOutboxItems] = useState([]);
  const { getPartyOutbox } = usePartyService();

  useEffect(() => {
    if (partyId) {
      getPartyOutbox(partyId).then(res => setOutboxItems(res.data));
    }
  }, [partyId]);

  if (!partyId) return <div>No party selected.</div>;

  return (
    <div className="party-outbox-tab">
      <h3>Outbox</h3>
      {outboxItems.length === 0 ? (
        <p>No outbound messages or actions found.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Message Type</th>
              <th>Status</th>
              <th>Target</th>
              <th>Sent At</th>
              <th>Retries</th>
            </tr>
          </thead>
          <tbody>
            {outboxItems.map((item, index) => (
              <tr key={index}>
                <td>{item.messageType}</td>
                <td>{item.status}</td>
                <td>{item.target}</td>
                <td>{new Date(item.sentAt).toLocaleString()}</td>
                <td>{item.retryCount}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default PartyOutboxTab;
