import React, { createContext, useContext, useState, useEffect } from 'react';

const TenantContext = createContext();

export const TenantProvider = ({ children }) => {
  const [tenantId, setTenantId] = useState(() => {
    // Fallback for local dev or first-time load
    const stored = localStorage.getItem('tenantId');
    return stored && stored.trim().length > 0
      ? stored
      : '5a32ef31-cdf6-4138-869d-511657a67f16'; // ✅ fallback UUID
  });

  const updateTenantId = (id) => {
    if (id && typeof id === 'string' && id.trim().length > 0) {
      localStorage.setItem('tenantId', id);
      setTenantId(id.trim());
      console.log('✅ Updated tenantId:', id);
    } else {
      console.warn('❌ Tried to set invalid tenantId:', id);
    }
  };

  useEffect(() => {
    console.log('🔍 Loaded tenantId from context:', tenantId);
  }, [tenantId]);

  return (
    <TenantContext.Provider value={{ tenantId, updateTenantId }}>
      {children}
    </TenantContext.Provider>
  );
};

export const useTenant = () => useContext(TenantContext);
