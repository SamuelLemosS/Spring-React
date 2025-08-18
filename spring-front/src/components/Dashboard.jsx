import React, { useState } from 'react';
import store from '../services/store'; // Zustand store
import { getEmailStats } from '../services/api';

const Dashboard = () => {
  const { userId, clearAuth } = store((state) => ({
    userId: state.userId,
    clearAuth: state.clearAuth,
  }));

  const [status, setStatus] = useState(null);
  const [loadingStatus, setLoadingStatus] = useState(false);

  const handleLogout = () => {
    clearAuth();
  };

  const handleGetStatus = async () => {
    if (!userId) return;

    setLoadingStatus(true);
    try {
      const data = await getEmailStats(); // já pega token do store
      setStatus(data);
    } catch (error) {
      setStatus({ error: typeof error === 'string' ? error : 'Erro ao buscar status' });
    } finally {
      setLoadingStatus(false);
    }
  };

  return (
    <div className="dashboard-container">
      <main className="dashboard-main">
        <div className="login-section">
          <h1>Obrigado por logar!</h1>

          <div className="dashboard-buttons">
            <button onClick={handleLogout} className="btn-logout">
              Sair
            </button>

            <button onClick={handleGetStatus} className="btn-primary" disabled={loadingStatus}>
              {loadingStatus ? 'Carregando...' : 'Ver Status'}
            </button>
          </div>

          {status && (
            <div className="status-result">
              <h3>Status do Email:</h3>
              <pre>{JSON.stringify(status, null, 2)}</pre>
            </div>
          )}
        </div>
      </main>
    </div>
  );
};

export default Dashboard;
