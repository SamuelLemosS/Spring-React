import axios from "axios";

import store from "./store";
const baseUrl = "http://localhost:8080/api";

const api = axios.create({
  baseURL: baseUrl,
  headers: {
    "Content-Type": "application/json",
  },
});

// ------------------ ROTAS ------------------ //

// Registrar -> /auth/register
export const register = async (name, email, password) => {
  try {
    const response = await api.post("/auth/register", {
      name,
      email,
      password,
    });
    return response.data; // string de confirmação
  } catch (error) {
    throw error.response?.data || "Erro no registro";
  }
};

// Login -> /auth/login
export const login = async (email, password) => {
  try {
    const response = await api.post("/auth/login", {
      email,
      password,
    });

    return response.data;
  } catch (error) {
    throw error.response?.data || "Erro no login";
  }
};

// Email stats -> /email/stats/{userId}
export const getEmailStats = async (userId,token) => {
  try {
    const response = await api.get(`/email/stats/${userId}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
  } catch (error) {
    throw error.response?.data || "Erro ao buscar estatísticas de email";
  }
};

export default api;
