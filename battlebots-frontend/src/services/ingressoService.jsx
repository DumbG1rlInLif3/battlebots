import api from "../api/api.jsx";

// ADMIN – listar todos ingressos
export const listarTodosIngressos = () => api.get("/ingressos");

// ADMIN – deletar ingresso
export const deletarIngresso = (id) => api.delete(`/ingressos/${id}`);

// USER – comprar ingresso
export const comprarIngresso = (payload) => api.post("/ingressos/comprar", payload);

// USER – listar meus ingressos
export const listarMeusIngressos = (usuarioId) => api.get(`/ingressos/meus/${usuarioId}`);

// Listar ingressos por evento (USER ou ADMIN)
export const listarPorEvento = (eventoId) => api.get(`/ingressos/eventos/${eventoId}`);
