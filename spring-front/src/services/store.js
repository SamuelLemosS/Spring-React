import { create } from "zustand";

// Função para carregar dados do localStorage
const getInitialState = () => ({
  token: localStorage.getItem("token") || null,
  userId: localStorage.getItem("userId") || null,
});

const store = create((set) => ({
  ...getInitialState(),

  // Salvar token e userId
  setAuth: (token, userId) => {
    localStorage.setItem("token", token);
    localStorage.setItem("userId", userId);
    set({ token, userId });
  },

  // Limpar token e userId (logout)
  clearAuth: () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    set({ token: null, userId: null });
  },
}));

export default store;
