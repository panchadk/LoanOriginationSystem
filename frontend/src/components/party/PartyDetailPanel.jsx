import React, { useState, useEffect } from 'react';
import { usePartyService } from './hooks/usePartyService';

import PartySummaryTab from './tabs/PartySummaryTab';
import PersonDetailsTab from './tabs/PersonDetailsTab';
import OrganizationDetailsTab from './tabs/OrganizationDetailsTab';
import PartyAddressTab from './tabs/PartyAddressTab';
import PartyBankAccountTab from './tabs/PartyBankAccountTab';
import PartyAuditTab from './tabs/PartyAuditTab';
import PartyCivilStatusTab from './tabs/PartyCivilStatusTab';
import PartyContactTab from './tabs/PartyContactTab';
import PartyIdentifierTab from './tabs/PartyIdentifierTab';
import PartyOutboxTab from './tabs/PartyOutboxTab';
import PartyRelationshipTab from './tabs/PartyRelationshipTab';

const PartyDetailPanel = ({ selectedPartyId }) => {
  const [activeTab, setActiveTab] = useState('summary');
  const [partyDetails, setPartyDetails] = useState(null);
  const [auditEvents, setAuditEvents] = useState([]);
  const { getPartyDetails, getAuditTrail } = usePartyService();

  useEffect(() => {
    if (selectedPartyId) {
      getPartyDetails(selectedPartyId).then(res => setPartyDetails(res.data));
      getAuditTrail(selectedPartyId).then(res => setAuditEvents(res.data));
    }
  }, [selectedPartyId]);

  const renderTab = () => {
    switch (activeTab) {
      case 'summary':
        return <PartySummaryTab data={partyDetails} />;
     case 'person':
       return (
         <PersonDetailsTab
           data={partyDetails?.person}
           readOnly={true}
         />
       );
      case 'organization':
        return (
          <OrganizationDetailsTab
            data={{
              kind: partyDetails?.kind,
              ...partyDetails?.organization,
            }}
            readOnly={true}
          />
        );
      case 'address':
        return <PartyAddressTab partyId={selectedPartyId} />;
      case 'bank':
        return <PartyBankAccountTab partyId={selectedPartyId} />;
      case 'audit':
        return <PartyAuditTab events={auditEvents} />;
      case 'civil':
        return <PartyCivilStatusTab data={partyDetails} />;
      case 'contact':
        return <PartyContactTab partyId={selectedPartyId} />;
      case 'identifier':
        return <PartyIdentifierTab partyId={selectedPartyId} />;
      case 'outbox':
        return <PartyOutboxTab partyId={selectedPartyId} />;
      case 'relationship':
        return <PartyRelationshipTab partyId={selectedPartyId} />;
      default:
        return null;
    }
  };

  return (
    <div className="party-detail-panel">
      <div className="tab-header">
        <button onClick={() => setActiveTab('summary')}>Summary</button>
        {partyDetails?.kind === 'PERSON' && (
          <>
            <button onClick={() => setActiveTab('person')}>Person</button>
            <button onClick={() => setActiveTab('civil')}>Civil Status</button>
          </>
        )}
        {partyDetails?.kind === 'ORGANIZATION' && (
          <button onClick={() => setActiveTab('organization')}>Organization</button>
        )}
        <button onClick={() => setActiveTab('address')}>Address</button>
        <button onClick={() => setActiveTab('bank')}>Bank</button>
        <button onClick={() => setActiveTab('contact')}>Contact</button>
        <button onClick={() => setActiveTab('identifier')}>Identifiers</button>
        <button onClick={() => setActiveTab('relationship')}>Relationships</button>
        <button onClick={() => setActiveTab('outbox')}>Outbox</button>
        <button onClick={() => setActiveTab('audit')}>Audit</button>
      </div>
      <div className="tab-content">{renderTab()}</div>
    </div>
  );
};

export default PartyDetailPanel;
