import { useState } from "react";
import { Tabs, Tab, Form, Button } from "react-bootstrap";

export default function IntakeStage() {
  const [key, setKey] = useState("broker");

  return (
    <div style={{ padding: "16px" }}>
      <h4>Intake Stage</h4>
      <Tabs
        id="intake-tabs"
        activeKey={key}
        onSelect={(k) => setKey(k)}
        className="mb-3"
      >
        {/* Broker Tab */}
        <Tab eventKey="broker" title="Broker">
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Broker Name</Form.Label>
              <Form.Control type="text" placeholder="Enter broker name" />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Broker Contact</Form.Label>
              <Form.Control type="text" placeholder="Enter contact info" />
            </Form.Group>
            <Button variant="primary">Save Broker</Button>
          </Form>
        </Tab>

        {/* Borrower Tab */}
        <Tab eventKey="borrower" title="Borrower">
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Borrower Name</Form.Label>
              <Form.Control type="text" placeholder="Enter borrower name" />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Borrower Contact</Form.Label>
              <Form.Control type="text" placeholder="Enter contact info" />
            </Form.Group>
            <Button variant="primary">Save Borrower</Button>
          </Form>
        </Tab>

        {/* Property Documents Tab */}
        <Tab eventKey="property" title="Property Documents">
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Upload Property Document</Form.Label>
              <Form.Control type="file" />
            </Form.Group>
            <Button variant="primary">Upload</Button>
          </Form>
        </Tab>

        {/* Deal Notes Tab */}
        <Tab eventKey="notes" title="Deal Notes">
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Notes</Form.Label>
              <Form.Control as="textarea" rows={4} placeholder="Enter notes" />
            </Form.Group>
            <Button variant="primary">Save Notes</Button>
          </Form>
        </Tab>
      </Tabs>
    </div>
  );
}
