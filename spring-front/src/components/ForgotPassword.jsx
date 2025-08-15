import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { useSnackBar } from '../contexts/SnackBarContext';
import './ForgotPassword.css';

const ForgotPassword = () => {
  const [email, setEmail] = useState('');
  const [loading, setLoading] = useState(false);
  const [emailSent, setEmailSent] = useState(false);
  
  const { forgotPassword } = useAuth();
  const { showSuccess, showError } = useSnackBar();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const result = await forgotPassword(email);
      
      if (result.success) {
        showSuccess(result.message);
        setEmailSent(true);
      } else {
        showError(result.message);
      }
    } catch (error) {
      showError('Erro inesperado ao enviar email');
    } finally {
      setLoading(false);
    }
  };

  if (emailSent) {
    return (
      <div className="forgot-password-container">
        <div className="forgot-password-card">

          <div className="success-message">
            <div className="success-icon">✓</div>
            <h2>Email Enviado!</h2>
            <p>
              Enviamos um email para <strong>{email}</strong> com instruções para redefinir sua senha.
            </p>
            <p>
              Verifique sua caixa de entrada e siga as instruções no email.
            </p>
          </div>

          <div className="forgot-password-links">
            <Link to="/login" className="btn-secondary">
              Voltar para o Login
            </Link>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="forgot-password-container">
      <div className="forgot-password-card">

        <form onSubmit={handleSubmit} className="forgot-password-form">
          <h2>Esqueci minha senha</h2>
          <p className="form-description">
            Digite seu email e enviaremos instruções para redefinir sua senha.
          </p>
          
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              placeholder="Digite seu email"
            />
          </div>

          <button 
            type="submit" 
            className="btn-primary"
            disabled={loading}
          >
            {loading ? 'Enviando...' : 'Enviar Email'}
          </button>

          <div className="forgot-password-links">
            <Link to="/login" className="link-back">
              Voltar para o Login
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ForgotPassword;
