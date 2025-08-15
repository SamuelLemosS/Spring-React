import React from 'react';
import { useSnackBar } from '../contexts/SnackBarContext';
import './SnackBar.css';

const SnackBar = () => {
  const { snackBar, hideSnackBar } = useSnackBar();

  if (!snackBar.open) return null;

  const getIcon = () => {
    switch (snackBar.type) {
      case 'success':
        return '✓';
      case 'error':
        return '✕';
      case 'warning':
        return '⚠';
      case 'info':
        return 'ℹ';
      default:
        return '✓';
    }
  };

  const getBackgroundColor = () => {
    switch (snackBar.type) {
      case 'success':
        return '#4caf50';
      case 'error':
        return '#f44336';
      case 'warning':
        return '#ff9800';
      case 'info':
        return '#2196f3';
      default:
        return '#4caf50';
    }
  };

  React.useEffect(() => {
    const timer = setTimeout(() => {
      hideSnackBar();
    }, 4000);

    return () => clearTimeout(timer);
  }, [snackBar.open, hideSnackBar]);

  return (
    <div 
      className="snackbar"
      style={{ backgroundColor: getBackgroundColor() }}
    >
      <div className="snackbar-content">
        <span className="snackbar-icon">{getIcon()}</span>
        <span className="snackbar-message">{snackBar.message}</span>
        <button className="snackbar-close" onClick={hideSnackBar}>
          ✕
        </button>
      </div>
    </div>
  );
};

export default SnackBar;
