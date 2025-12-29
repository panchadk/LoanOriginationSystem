import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css';
import { TenantProvider } from './context/TenantContext'; // ✅ Import the provider

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <TenantProvider>
      <App />
    </TenantProvider>
  </React.StrictMode>
);

// Optional performance logging
reportWebVitals();
