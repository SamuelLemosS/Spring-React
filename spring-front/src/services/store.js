import { create } from "zustand";

const store = create((set) => ({
  id: null,
  token: null,

  setId: (id) => set({ id }),
  setToken: (token) => set({ token }),
  clearAuth: () => set({ id: null, token: null }),
}));

export default store;
