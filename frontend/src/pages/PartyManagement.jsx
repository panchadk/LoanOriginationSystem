import { useEffect, useState, useMemo } from 'react';
import { Row, Col, Button, Tab, Tabs, Form } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import {
  useReactTable,
  getCoreRowModel,
  getPaginationRowModel,
  flexRender,
} from '@tanstack/react-table';

import PartyCreateEditModal from '../components/party/PartyCreateEditModal';
import PartyContactTab from '../components/party/tabs/PartyContactTab';
import PartyAddressTab from '../components/party/tabs/PartyAddressTab';
import PartyIdentifierTab from '../components/party/tabs/PartyIdentifierTab';
import PartyCivilStatusTab from '../components/party/tabs/PartyCivilStatusTab';
import PartyRelationshipTab from '../components/party/tabs/PartyRelationshipTab';
import PartyBankAccountTab from '../components/party/tabs/PartyBankAccountTab';
import PartyAuditTab from '../components/party/tabs/PartyAuditTab';
import PartyOutboxTab from '../components/party/tabs/PartyOutboxTab';

export default function PartyManagement() {
  const { tenantSlug } = useParams();

  const tenantMap = {
    timhortons: '60716764-6f96-4ff7-a30d-08b9dacf1591',
    starbucks: 'a1b2c3d4-5678-90ef-ghij-klmnopqrstuv',
  };

  const tenantId = tenantMap[tenantSlug];

  const [parties, setParties] = useState([]);
  const [search, setSearch] = useState('');
  const [selectedParty, setSelectedParty] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [editParty, setEditParty] = useState(null);

  const loadParties = () => {
    fetch(`/api/party?search=${encodeURIComponent(search)}`, {
      headers: { 'X-Tenant-ID': tenantId },
    })
      .then((res) => res.json())
      .then((data) => {
        setParties(Array.isArray(data) ? data : []);
      })
      .catch((err) => {
        console.error('Error fetching parties:', err);
        setParties([]);
      });
  };

  useEffect(() => {
    if (tenantId) loadParties();
  }, [search, tenantId]);

  const handleDelete = async (id) => {
    await fetch(`/api/party/${id}`, {
      method: 'DELETE',
      headers: { 'X-Tenant-ID': tenantId },
    });
    loadParties();
  };

  const columns = useMemo(
    () => [
      {
        accessorKey: 'displayName',
        header: 'Name',
        cell: ({ row }) =>
          row.original.kind === 'PERSON'
            ? `${row.original.givenName ?? ''} ${row.original.familyName ?? ''}`.trim()
            : row.original.legalName,
      },
      {
        accessorKey: 'status',
        header: 'Status',
        cell: ({ getValue }) => (
          <span className={`status-badge ${getValue().toLowerCase()}`}>{getValue()}</span>
        ),
      },
      {
        accessorKey: 'edit',
        header: 'Edit',
        cell: ({ row }) => (
          <Button
            variant="outline-primary"
            size="sm"
            onClick={() => {
              setEditParty(row.original);
              setShowModal(true);
            }}
          >
            Edit
          </Button>
        ),
      },
      {
        accessorKey: 'delete',
        header: 'Delete',
        cell: ({ row }) => (
          <Button
            variant="outline-danger"
            size="sm"
            onClick={() => handleDelete(row.original.partyId)}
          >
            Delete
          </Button>
        ),
      },
      {
        accessorKey: 'details',
        header: 'Details',
        cell: ({ row }) => (
          <Button
            variant="outline-secondary"
            size="sm"
            onClick={() => setSelectedParty(row.original)}
          >
            View
          </Button>
        ),
      },
    ],
    []
  );

  const table = useReactTable({
    data: parties,
    columns,
    getCoreRowModel: getCoreRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
  });

  return (
    <>
      <Row className="mb-3">
        <Col>
          <Button
            variant="success"
            onClick={() => {
              setEditParty(null);
              setShowModal(true);
            }}
          >
            Create Party
          </Button>
        </Col>
        <Col>
          <Form.Control
            type="text"
            placeholder="Search parties..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
        </Col>
      </Row>

      <Row>
        <Col>
          <table className="table table-striped table-hover table-condensed">
            <thead>
              {table.getHeaderGroups().map((headerGroup) => (
                <tr key={headerGroup.id}>
                  {headerGroup.headers.map((header) => (
                    <th key={header.id}>
                      {flexRender(header.column.columnDef.header, header.getContext())}
                    </th>
                  ))}
                </tr>
              ))}
            </thead>
            <tbody>
              {table.getRowModel().rows.map((row) => (
                <tr key={row.id}>
                  {row.getVisibleCells().map((cell) => (
                    <td key={cell.id}>
                      {flexRender(cell.column.columnDef.cell, cell.getContext())}
                    </td>
                  ))}
                </tr>
              ))}
            </tbody>
          </table>
        </Col>
      </Row>

      {selectedParty && (
        <Row className="mt-4">
          <Col>
            <Tabs defaultActiveKey="identity" className="mb-3">
              <Tab eventKey="contacts" title="Contacts">
                <PartyContactTab partyId={selectedParty.partyId} tenantId={tenantId} />
              </Tab>
              <Tab eventKey="addresses" title="Addresses">
                <PartyAddressTab partyId={selectedParty.partyId} tenantId={tenantId} />
              </Tab>
              <Tab eventKey="identifiers" title="Identifiers">
                <PartyIdentifierTab partyId={selectedParty.partyId} tenantId={tenantId} />
              </Tab>
              <Tab eventKey="civil" title="Civil Status">
                <PartyCivilStatusTab partyId={selectedParty.partyId} tenantId={tenantId} />
              </Tab>
              <Tab eventKey="relationships" title="Relationships">
                <PartyRelationshipTab partyId={selectedParty.partyId} tenantId={tenantId} />
              </Tab>
              <Tab eventKey="bank" title="Bank Accounts">
                <PartyBankAccountTab partyId={selectedParty.partyId} tenantId={tenantId} />
              </Tab>
              <Tab eventKey="audit" title="Audit Trail">
                <PartyAuditTab partyId={selectedParty.partyId} tenantId={tenantId} />
              </Tab>
              <Tab eventKey="outbox" title="Outbox Events">
                <PartyOutboxTab partyId={selectedParty.partyId} tenantId={tenantId} />
              </Tab>
            </Tabs>
          </Col>
        </Row>
      )}

      {showModal && (
        <PartyCreateEditModal
          party={editParty}
          tenantId={tenantId} // ✅ Pass tenantId to modal
          onClose={() => {
            setShowModal(false);
            setEditParty(null);
            loadParties();
          }}
        />
      )}
    </>
  );
}
