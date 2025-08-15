import React from 'react';
import { useAuth } from '../contexts/AuthContext';
import './Dashboard.css';

const Dashboard = () => {
  const { user, logout } = useAuth();

  const handleLogout = async () => {
    await logout();
  };

  return (
    <div className="dashboard-container">
      <main className="dashboard-main">
        <div className="login-section">
          <h1>Obrigado por logar, {user?.name}!</h1>
          <button onClick={handleLogout} className="btn-logout">
            Sair
          </button>
        </div>
      </main>
    </div>
  );
};

export default Dashboard;
