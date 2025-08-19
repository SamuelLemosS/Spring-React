import React, { useState } from 'react';
import store from '../services/store'; 
import { getEmailStats } from '../services/api';
import { useSnackBar } from '../contexts/SnackBarContext';
import { Link, useNavigate } from 'react-router-dom';
import './Dashboard.css'

const Dashboard = () => {
  const {  clearAuth,token,id } = store();
  const navigate = useNavigate();
  const { showSuccess, showError } = useSnackBar();

  const [status, setStatus] = useState(null);
  const [loadingStatus, setLoadingStatus] = useState(false);

  const handleLogout = () => {
    clearAuth();
    navigate('/login');
  };

  const handleGetStatus = async () => {
    if (!id) return;

    setLoadingStatus(true);
    try {
      const data = await getEmailStats(id,token); 
      setStatus(data);
      showSuccess(data);
    } catch (error) {
      showError(typeof error === 'string' ? error : 'Erro inesperado ao fazer login');
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
            <button onClick={handleGetStatus} className="btn-logout" disabled={loadingStatus}>
              {loadingStatus ? 'Carregando...' : 'Enviar email de status resumido'}
            </button>
            <button onClick={handleLogout} className="btn-logout">
              Sair
            </button>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Dashboard;
