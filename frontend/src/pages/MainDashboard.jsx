import { useAuth } from '../utils/useAuth';
import { useState } from 'react';
import { Container, Row, Col, Nav, Button } from 'react-bootstrap';
import PartyManagement from './PartyManagement';
import KanbanBoard from '../components/kanban/KanbanBoard';

// import DealIntake from './DealIntake';
// import DocumentCenter from './DocumentCenter';

export default function MainDashboard() {
  const [activeModule, setActiveModule] = useState('party');
  const [collapsed, setCollapsed] = useState(false);

  const renderModule = () => {
    switch (activeModule) {
      case 'party':
        return <PartyManagement />;
      case 'deal':
        return <KanbanBoard />;
      // case 'docs':
      //   return <DocumentCenter />;
      default:
        return <p>Select a module from the left panel.</p>;
    }
  };

  return (
    <Container fluid className="mt-4">
      <Row>
        {/* Left Panel Navigation */}
        <Col md={collapsed ? 1 : 2} className="bg-light">
          <div className="d-flex justify-content-between align-items-center mb-3">
            {!collapsed && <h6 className="mb-0">Admin Navigation</h6>}
            <Button
              variant="link"
              size="sm"
              onClick={() => setCollapsed(!collapsed)}
              style={{ textDecoration: 'none' }}
            >
              {collapsed ? '➡️' : '⬅️'}
            </Button>
          </div>

          <Nav className="flex-column">
            <Nav.Link
              active={activeModule === 'party'}
              onClick={() => setActiveModule('party')}
            >
              {collapsed ? '👥' : 'Party Management'}
            </Nav.Link>
            <Nav.Link
              active={activeModule === 'deal'}
              onClick={() => setActiveModule('deal')}
            >
              {collapsed ? '💼' : 'Deal Intake'}
            </Nav.Link>
            <Nav.Link
              active={activeModule === 'docs'}
              onClick={() => setActiveModule('docs')}
            >
              {collapsed ? '📄' : 'Document Center'}
            </Nav.Link>
          </Nav>
        </Col>

        {/* Right Panel Content */}
        <Col md={collapsed ? 11 : 10}>
          {renderModule()}
        </Col>
      </Row>
    </Container>
  );
}
