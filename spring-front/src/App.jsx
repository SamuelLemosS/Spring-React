import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { SnackBarProvider } from './contexts/SnackBarContext';
import ProtectedRoute from './components/ProtectedRoute';
import Login from './components/Login';
import Register from './components/Register';
import Dashboard from './components/Dashboard';
import SnackBar from './components/SnackBar';
import './App.css';

function App() {
  return (
      <SnackBarProvider>
        <Router>
          <div className="App">
            <Routes>
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route 
                path="/dashboard" 
                element={
                  
                    <Dashboard />
                 
                } 
              />
              <Route path="/" element={<Navigate to="/login" replace />} />
            </Routes>
            <SnackBar />
          </div>
        </Router>
      </SnackBarProvider>
  );
}

export default App;
