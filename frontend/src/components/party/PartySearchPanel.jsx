import React, { useEffect, useState } from 'react';
import PartyCreateEditModal from './PartyCreateEditModal';
import { usePartyService } from '../hooks/usePartyService';

const PartySearchPanel = ({ onSelectParty }) => {
  const [parties, setParties] = useState([]);
  const [search, setSearch] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [editParty, setEditParty] = useState(null);
  const [error, setError] = useState(null);

  const { searchParties, deleteParty, toggleStatus } = usePartyService();

  const loadParties = async () => {
    try {
      const query = search.trim() === '' ? 'Smith' : search;
      const res = await searchParties(query);
      setParties(res.data);
      setError(null);
    } catch (err) {
      console.error('Error fetching parties:', err);
      setParties([]);
      setError(err.response?.data?.message || 'Failed to load parties.');
    }
  };

  useEffect(() => {
    loadParties();
  }, [search]);

  const handleEdit = (party) => {
    setEditParty(party);
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    await deleteParty(id);
    loadParties();
  };

  const handleToggleStatus = async (id, currentStatus) => {
    const newStatus = currentStatus === 'ACTIVE' ? 'INACTIVE' : 'ACTIVE';
    await toggleStatus(id, newStatus);
    loadParties();
  };

  return (
    <div className="party-search-panel">
      <div className="header">
        <input
          value={search}
          onChange={e => setSearch(e.target.value)}
          placeholder="Search parties..."
        />
        <button onClick={() => { setEditParty(null); setShowModal(true); }}>
          Create Party
        </button>
      </div>

      {error && <div className="error-message">{error}</div>}

      <ul className="party-list">
        {parties.map(p => (
          <li key={p.partyId} className="party-item">
            <span className="party-name" onClick={() => onSelectParty(p.partyId)}>
              {p.displayName}
            </span>
            <span className={`status-badge ${p.status.toLowerCase()}`}>{p.status}</span>
            <button onClick={() => handleEdit(p)}>Edit</button>
            <button onClick={() => handleToggleStatus(p.partyId, p.status)}>Toggle Status</button>
            <button onClick={() => handleDelete(p.partyId)}>Delete</button>
          </li>
        ))}
      </ul>

      {showModal && (
        <PartyCreateEditModal
          party={editParty}
          onClose={() => {
            setShowModal(false);
            loadParties();
          }}
        />
      )}
    </div>
  );
};

export default PartySearchPanel;
