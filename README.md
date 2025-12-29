Party & Identity Platform — Overview & Developer Guide
📌 Purpose of the System
This project implements a multi‑tenant Party & Identity Management Platform. It provides a canonical way to represent people, organizations, relationships, identifiers, contact points, addresses, and bank accounts across multiple tenants.

It is designed for financial, lending, onboarding, KYC, and workflow systems that require:

A single source of truth for all actors (customers, businesses, guarantors, employees, etc.)

Effective‑dated history (civil status, addresses, contacts)

Encrypted identifiers (SIN, passport, driver’s license)

Multi‑tenant isolation

Extensible relationship modeling

Regulatory‑grade auditability

🏗️ High‑Level Architecture
Backend
Java 17 / Spring Boot

PostgreSQL (multi‑tenant aware)

JPA/Hibernate for ORM

REST API for frontend integration

Frontend
React (localhost:3000)

Consumes /api/* endpoints

Database
PostgreSQL schema with:

Canonical party table

Person & organization facets

Effective‑dated contact, address, civil status

Encrypted identifiers

Bank account + mandate metadata

Relationship graph

🧩 Core Domain Concepts
1. Party
The root entity representing any actor in the system.

Field	Description
tenant_id	Multi‑tenant isolation key
party_id	UUID primary key
kind	PERSON or ORG
status	ACTIVE / INACTIVE
created_at	Timestamp
Every person or organization starts here.

2. Person (Facet)
1:1 extension of party for individuals.

Includes:

given_name

middle_name

family_name

dob

residency_status

3. Organization (Facet)
1:1 extension of party for businesses.

Includes:

legal_name

registration_jurisdiction

business_type

BIN (business identification number)

4. Civil Status (Effective‑Dated)
Tracks marital/civil status over time.

Primary key: (tenant_id, party_id, effective_from)

5. Party Identifier (Encrypted IDs)
Stores sensitive identifiers such as:

SIN

Passport

Driver’s license

Encrypted using:

value_ciphertext BYTEA

last4 for display

issuer, valid_from, valid_to

6. Contact Points
Email, phone, fax, portal accounts.

7. Party ↔ Contact (History)
Effective‑dated association between a party and a contact point.

8. Address
Stores physical address + optional geocode JSON.

9. Party ↔ Address (History)
Effective‑dated association for:

LEGAL

MAILING

SERVICE

10. Party Relationships
Graph of real‑world ties:

Parent/child

Director/officer

Guarantor/borrower

Employer/employee

11. Party Bank Account
Stores masked and hashed bank account details for ACH/PAD.

Includes:

routing_number_hash

account_no_hash

account_mask_last4

verification status

consent to debit

mandate metadata

effective‑dated validity

🚀 Running the Project
Prerequisites
Java 17+

Maven or Gradle

PostgreSQL 14+

Node.js  (for frontend)
