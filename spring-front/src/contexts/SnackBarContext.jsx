import React, { createContext, useContext, useState } from 'react';

const SnackBarContext = createContext();

export const useSnackBar = () => {
  const context = useContext(SnackBarContext);
  if (!context) {
    throw new Error('useSnackBar deve ser usado dentro de um SnackBarProvider');
  }
  return context;
};

export const SnackBarProvider = ({ children }) => {
  const [snackBar, setSnackBar] = useState({
    open: false,
    message: '',
    type: 'success', // 'success', 'error', 'info', 'warning'
  });

  const showSnackBar = (message, type = 'success') => {
    setSnackBar({
      open: true,
      message,
      type,
    });
  };

  const hideSnackBar = () => {
    setSnackBar(prev => ({
      ...prev,
      open: false,
    }));
  };

  const showSuccess = (message) => showSnackBar(message, 'success');
  const showError = (message) => showSnackBar(message, 'error');
  const showInfo = (message) => showSnackBar(message, 'info');
  const showWarning = (message) => showSnackBar(message, 'warning');

  const value = {
    snackBar,
    showSnackBar,
    hideSnackBar,
    showSuccess,
    showError,
    showInfo,
    showWarning,
  };

  return (
    <SnackBarContext.Provider value={value}>
      {children}
    </SnackBarContext.Provider>
  );
};
