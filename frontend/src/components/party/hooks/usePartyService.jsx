import axios from 'axios';
import { useTenant } from '../../../context/TenantContext';

export const usePartyService = () => {
  const { tenantId } = useTenant();

  // Validate tenantId
  const isValidTenantId = tenantId && typeof tenantId === 'string' && tenantId.trim().length > 0;

  if (!isValidTenantId) {
    console.error('❌ Missing or invalid tenantId — PartyService requests will fail');
  }

  const TENANT_HEADER = isValidTenantId
    ? {
        headers: {
          'X-Tenant-ID': tenantId.trim(),
        },
      }
    : {};

  const guard = () => {
    if (!isValidTenantId) {
      throw new Error('Missing or invalid tenantId — cannot perform PartyService request');
    }
  };

  const searchParties = async (search = '') => {
    guard();
    return axios.get(`/api/party?search=${search}`, TENANT_HEADER);
  };

  const getPartyDetails = async (id) => {
    guard();
    return axios.get(`/api/party/${id}`, TENANT_HEADER);
  };

  const getAuditTrail = async (id) => {
    guard();
    return axios.get(`/api/party/${id}/audit`, TENANT_HEADER);
  };

  const createParty = async (data) => {
    guard();
    return axios.post(`/api/party`, data, TENANT_HEADER);
  };

  const updateParty = async (id, data) => {
    guard();
    return axios.put(`/api/party/${id}`, data, TENANT_HEADER);
  };

  const deleteParty = async (id) => {
    guard();
    return axios.delete(`/api/party/${id}`, TENANT_HEADER);
  };

  const toggleStatus = async (id, status) => {
    guard();
    return axios.patch(`/api/party/${id}/status?status=${status}`, null, TENANT_HEADER);
  };

  const getPartyAddresses = async (id) => {
    guard();
    return axios.get(`/api/party/${id}/address`, TENANT_HEADER);
  };

  const getPartyBankAccounts = async (id) => {
    guard();
    return axios.get(`/api/party/${id}/bank`, TENANT_HEADER);
  };

  const getPartyContacts = async (id) => {
    guard();
    return axios.get(`/api/party/${id}/contact`, TENANT_HEADER);
  };

  const getPartyIdentifiers = async (id) => {
    guard();
    return axios.get(`/api/party/${id}/identifier`, TENANT_HEADER);
  };

  const getPartyOutbox = async (id) => {
    guard();
    return axios.get(`/api/party/${id}/outbox`, TENANT_HEADER);
  };

  const getPartyRelationships = async (id) => {
    guard();
    return axios.get(`/api/party/${id}/relationship`, TENANT_HEADER);
  };

  return {
    searchParties,
    getPartyDetails,
    getAuditTrail,
    createParty,
    updateParty,
    deleteParty,
    toggleStatus,
    getPartyAddresses,
    getPartyBankAccounts,
    getPartyContacts,
    getPartyIdentifiers,
    getPartyOutbox,
    getPartyRelationships,
  };
};
