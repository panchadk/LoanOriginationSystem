import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import AppLayout from './components/AppLayout';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import ForgotPasswordPage from './pages/ForgotPasswordPage';
import ResetPasswordPage from './pages/ResetPasswordPage';
import MainDashboard from './pages/MainDashboard';
import ChooseDashboard from './pages/ChooseDashboard';
import UnauthorizedPage from './pages/UnauthorizedPage';
import AdminDashboard from './components/AdminDashboard';
import UserManagement from './components/UserManagement';
import RoleManagement from './components/RoleManagement';
import TenantManagement from './components/TenantManagement';
import PartyManagement from './pages/PartyManagement';
import RequireAdmin from './auth/RequireAdmin';

const App = () => {
  return (
    <Router>
      <Routes>
        {/* ✅ Redirect root to default tenant login */}
        <Route path="/" element={<Navigate to="/timhortons/login" />} />

        {/* ✅ Tenant-prefixed routes */}
        <Route path="/:tenantSlug/login" element={<LoginPage />} />
        <Route path="/:tenantSlug/register" element={<RegisterPage />} />
        <Route path="/:tenantSlug/forgot-password" element={<ForgotPasswordPage />} />
        <Route path="/:tenantSlug/reset-password" element={<ResetPasswordPage />} />

        {/* ✅ Routes with AppLayout */}
        <Route path="/:tenantSlug" element={<AppLayout />}>
          <Route path="dashboard" element={<MainDashboard />} />
          <Route path="choose-dashboard" element={<ChooseDashboard />} />
          <Route path="unauthorized" element={<UnauthorizedPage />} />
          <Route path="party" element={<PartyManagement />} />
        </Route>

        {/* ✅ Admin Dashboard with Nested Routes */}
        <Route
          path="/:tenantSlug/admin"
          element={
            <RequireAdmin>
              <AdminDashboard />
            </RequireAdmin>
          }
        >
          <Route index element={<Navigate to="users" />} />
          <Route path="users" element={<UserManagement mode="view" />} />
          <Route path="users/add" element={<UserManagement mode="add" />} />
          <Route path="users/edit/:userId" element={<UserManagement mode="edit" />} />
          <Route path="users/delete/:userId" element={<UserManagement mode="delete" />} />
          <Route path="roles" element={<RoleManagement mode="view" />} />
          <Route path="roles/add" element={<RoleManagement mode="add" />} />
          <Route path="roles/edit/:roleId" element={<RoleManagement mode="edit" />} />
          <Route path="roles/delete/:roleId" element={<RoleManagement mode="delete" />} />
          <Route path="tenants" element={<TenantManagement mode="view" />} />
          <Route path="tenants/add" element={<TenantManagement mode="add" />} />
          <Route path="tenants/edit/:tenantId" element={<TenantManagement mode="edit" />} />
          <Route path="tenants/delete/:tenantId" element={<TenantManagement mode="delete" />} />
        </Route>

        {/* ✅ Catch-all route for 404 */}
        <Route path="*" element={<div>404: Page not found</div>} />
      </Routes>
    </Router>
  );
};

export default App;
