h1 align="center">🌐 Party & Identity Platform</h1>
<h3 align="center">A Multi‑Tenant Engine for People, Organizations, Relationships & Identity Data</h3>
🚀 Overview
This project implements a multi‑tenant Party & Identity Management Platform.
It provides a canonical, extensible way to represent:

People & organizations

Contact points (email, phone, portal)

Addresses & geocodes

Encrypted identifiers (SIN, passport, DL)

Civil status history

Party relationships

Bank accounts & mandates

Effective‑dated historical records

This system is designed for onboarding, KYC, lending, workflow automation, and any domain requiring clean, auditable, regulatory‑grade identity data.

🏗️ Architecture
Backend
Java 17

Spring Boot

JPA/Hibernate

PostgreSQL

REST API (/api/*)

Frontend
React (localhost:3000)

Consumes backend REST endpoints

Database
PostgreSQL schema with strict multi‑tenant isolation

UUID primary keys

Effective‑dated tables

Encrypted identifiers

🧩 Domain Model Summary
1. Party (root entity)
Represents any actor in the system.

Field	Description
tenant_id	Tenant isolation key
party_id	UUID primary key
kind	PERSON / ORG
status	ACTIVE / INACTIVE
created_at	Timestamp
2. Person (facet of Party)
1:1 extension for individuals.

Includes:
given_name, middle_name, family_name, dob, residency_status

3. Organization (facet of Party)
1:1 extension for businesses.

Includes:
legal_name, business_type, registration_jurisdiction, bin

4. Civil Status (effective‑dated)
Tracks marital/civil status over time.

Primary key:
(tenant_id, party_id, effective_from)

5. Party Identifier (encrypted)
Stores sensitive identifiers:

SIN

Passport

Driver’s license

Encrypted fields:
value_ciphertext BYTEA, last4, issuer, valid_from, valid_to

6. Contact Point
Email, phone, mobile, fax, portal accounts.

7. Party ↔ Contact (history)
Effective‑dated association with preferred flag.

8. Address
Physical address + optional geocode JSON.

9. Party ↔ Address (history)
Effective‑dated association for:

LEGAL

MAILING

SERVICE

10. Party Relationship
Graph of real‑world ties:

Parent/child

Director/officer

Guarantor/borrower

Employer/employee

11. Party Bank Account
Masked & hashed ACH/PAD account details.

Includes:
routing_number_hash, account_no_hash, mask_last4, verification_status, consent_to_debit, mandate_id, limits, effective dates

🛠️ Running the Backend
1. Create PostgreSQL database
sql
CREATE DATABASE party_identity;
2. Configure Spring Boot
application.properties:

properties
spring.datasource.url=jdbc:postgresql://localhost:5432/party_identity
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
3. Start the backend
bash
mvn spring-boot:run
API available at:

Code
http://localhost:8080/api
🎨 Running the Frontend
bash
cd frontend
npm install
npm start
Runs at:

Code
http://localhost:3000
📚 API Examples
GET /api/roles
Returns all roles with tenant mapping.

GET /api/party/{id}
Fetches a canonical party.

GET /api/party/{id}/contacts
Returns contact history.

GET /api/party/{id}/addresses
Returns address history.

🧪 Testing
Use curl or Postman:

bash
curl http://localhost:8080/api/roles
🛡️ Development Guidelines
Always include tenant_id in queries

Use UUIDs for all primary keys

Use effective‑dating for historical tables

Never store raw identifiers (always encrypted)

Use DTOs to avoid exposing internal entities

🔮 Future Enhancements
KYC document ingestion

Consent management

Party risk scoring

Workflow integration

GraphQL API for relationship traversal

If you want, I can also:

Add badges (build, license, version)

Add a project logo

Add a database diagram

Add a “Quick Start” section

Add a “Contributing” section

PostgreSQL 14+

Node.js  (for frontend)
