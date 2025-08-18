import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useSnackBar } from '../contexts/SnackBarContext';
import { login as apiLogin } from '../services/api'; // import da API
import store from '../services/store'; // Zustand store
import './Login.css';

const Login = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });
  const [loading, setLoading] = useState(false);

  const { showSuccess, showError } = useSnackBar();
  const setAuth = store((state) => state.setAuth); // função para salvar token e userId
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      // Chamada da API de login
      const data = await apiLogin(formData.email, formData.password);

      // Salva token e userId no store e localStorage
      setAuth(data.token, data.id);

      // Mostra SnackBar de sucesso
      showSuccess('Login realizado com sucesso!');

      // Redireciona para dashboard
      navigate('/dashboard');
    } catch (error) {
      // Mostra erro no SnackBar
      showError(typeof error === 'string' ? error : 'Erro inesperado ao fazer login');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <form onSubmit={handleSubmit} className="login-form">
          <h2>Login</h2>

          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
              placeholder="Digite seu email"
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Senha</label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
              placeholder="Digite sua senha"
            />
          </div>

          <button type="submit" className="btn-primary" disabled={loading}>
            {loading ? 'Entrando...' : 'Entrar'}
          </button>

          <div className="login-links">
            <div className="register-link">
              Não tem uma conta?{' '}
              <Link to="/register" className="link-register">
                Registre-se
              </Link>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Login;
