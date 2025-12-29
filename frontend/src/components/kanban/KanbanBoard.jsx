import React, { useEffect, useState } from "react";
import { DragDropContext, Droppable, Draggable } from "react-beautiful-dnd";
import { Dialog, Tabs, Tab, TextField, Button } from "@mui/material";
import api from "../../utils/axiosConfig";

const stages = ["PREQUAL", "UNDER_REVIEW", "APPROVED", "FUNDED", "CLOSED", "ARCHIVED"];

export default function KanbanBoard() {
  const [dealsByStage, setDealsByStage] = useState({});
  const [openDialog, setOpenDialog] = useState(false);
  const [tabIndex, setTabIndex] = useState(0);

  const [newDeal, setNewDeal] = useState({
    productCode: "",
    amountRequested: "",
    stage: "PREQUAL"
  });

  const [partyForms, setPartyForms] = useState({
    borrower: { name: "", email: "", phone: "" },
    broker: { name: "", email: "", phone: "" },
    guarantor: { name: "", email: "", phone: "" }
  });

  // Load Kanban board
  useEffect(() => {
    api.get("/deal/by-stage")
      .then(res => setDealsByStage(res.data))
      .catch(err => console.error("Failed to load deals", err));
  }, []);

  // Drag-and-drop stage update
  const handleDragEnd = (result) => {
    const { draggableId, source, destination } = result;
    if (!destination || source.droppableId === destination.droppableId) return;

    api.patch(`/deal/${draggableId}`, {
      newStage: destination.droppableId,
      notes: `Moved from ${source.droppableId} to ${destination.droppableId}`,
      amount: null
    }).then(() => {
      api.get("/deal/by-stage").then(res => setDealsByStage(res.data));
    }).catch(err => console.error("Stage update failed", err));
  };

  // Create new deal + parties
  const handleCreateDeal = async () => {
    try {
      const dealRes = await api.post("/deal", newDeal);
      const dealId = dealRes.data.dealId;

      for (const role of ["borrower", "broker", "guarantor"]) {
        const party = partyForms[role];
        if (party.name) {
          await api.post(`/deal/${dealId}/${role}`, party);
        }
      }

      setOpenDialog(false);
      api.get("/deal/by-stage").then(res => setDealsByStage(res.data));
    } catch (err) {
      console.error("Failed to create deal", err);
    }
  };

  return (
    <div>
      <Button variant="contained" onClick={() => setOpenDialog(true)}>Create Deal</Button>

      <DragDropContext onDragEnd={handleDragEnd}>
        <div style={{ display: "flex", gap: "16px", marginTop: "20px" }}>
          {stages.map(stage => (
            <Droppable droppableId={stage} key={stage}>
              {(provided) => (
                <div
                  ref={provided.innerRef}
                  {...provided.droppableProps}
                  style={{ flex: 1, padding: "10px", background: "#f4f4f4", borderRadius: "8px" }}
                >
                  <h3>{stage}</h3>
                  {(dealsByStage[stage] || []).map((deal, index) => (
                    <Draggable draggableId={deal.dealId} index={index} key={deal.dealId}>
                      {(provided) => (
                        <div
                          ref={provided.innerRef}
                          {...provided.draggableProps}
                          {...provided.dragHandleProps}
                          style={{
                            padding: "10px",
                            marginBottom: "8px",
                            background: "#fff",
                            borderRadius: "4px",
                            boxShadow: "0 1px 3px rgba(0,0,0,0.1)",
                            ...provided.draggableProps.style
                          }}
                        >
                          <strong>{deal.productCode}</strong><br />
                          ${deal.amountRequested}<br />
                          v{deal.stageVersion}
                        </div>
                      )}
                    </Draggable>
                  ))}
                  {provided.placeholder}
                </div>
              )}
            </Droppable>
          ))}
        </div>
      </DragDropContext>

      {/* Create Deal Modal */}
      <Dialog open={openDialog} onClose={() => setOpenDialog(false)} maxWidth="md" fullWidth>
        <div style={{ padding: "20px" }}>
          <h2>Create New Deal</h2>
          <TextField
            label="Product Code"
            fullWidth
            margin="normal"
            value={newDeal.productCode}
            onChange={e => setNewDeal({ ...newDeal, productCode: e.target.value })}
          />
          <TextField
            label="Amount Requested"
            fullWidth
            margin="normal"
            value={newDeal.amountRequested}
            onChange={e => setNewDeal({ ...newDeal, amountRequested: e.target.value })}
          />

          <Tabs value={tabIndex} onChange={(e, val) => setTabIndex(val)} style={{ marginTop: "20px" }}>
            <Tab label="Borrower" />
            <Tab label="Broker" />
            <Tab label="Guarantor" />
          </Tabs>

          {["borrower", "broker", "guarantor"].map((role, idx) => (
            tabIndex === idx && (
              <div key={role} style={{ marginTop: "20px" }}>
                <TextField
                  label="Name"
                  fullWidth
                  margin="normal"
                  value={partyForms[role].name}
                  onChange={e => setPartyForms({
                    ...partyForms,
                    [role]: { ...partyForms[role], name: e.target.value }
                  })}
                />
                <TextField
                  label="Email"
                  fullWidth
                  margin="normal"
                  value={partyForms[role].email}
                  onChange={e => setPartyForms({
                    ...partyForms,
                    [role]: { ...partyForms[role], email: e.target.value }
                  })}
                />
                <TextField
                  label="Phone"
                  fullWidth
                  margin="normal"
                  value={partyForms[role].phone}
                  onChange={e => setPartyForms({
                    ...partyForms,
                    [role]: { ...partyForms[role], phone: e.target.value }
                  })}
                />
              </div>
            )
          ))}

          <Button variant="contained" color="primary" onClick={handleCreateDeal} style={{ marginTop: "20px" }}>
            Save Deal
          </Button>
        </div>
      </Dialog>
    </div>
  );
}
