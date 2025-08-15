import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor para adicionar token de autenticação
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Interceptor para tratar erros de resposta
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export const authService = {
  // Login
  login: (email, password) => 
    api.post('/auth/login', { email, password }),
  
  // Registro
  register: (name, email, password) => 
    api.post('/auth/register', { name, email, password }),
  
  // Esqueci senha
  forgotPassword: (email) => 
    api.post('/auth/forgot-password', { email }),
  
  // Verificar token
  verifyToken: () => 
    api.get('/auth/verify'),
  
  // Logout
  logout: () => 
    api.post('/auth/logout'),
};

export const userService = {
  // Obter perfil do usuário
  getProfile: () => 
    api.get('/user/profile'),
  
  // Atualizar perfil
  updateProfile: (data) => 
    api.get('/user/profile', data),
};

export default api;
