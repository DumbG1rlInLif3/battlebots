// serviços de autenticação para o login
import authApi from "../api/authApi"; 

// login envia email/senha e recebe { token, role, nome }
export const login = (email, senha) => {
  return authApi.post("/auth/login", { email, senha });
};

// salva dados no localStorage
export const saveAuth = ({ token, role, nome }) => {
  localStorage.setItem("token", token);
  localStorage.setItem("role", role);
  localStorage.setItem("nome", nome);
  // dispara evento para o Navbar atualizar
  window.dispatchEvent(new Event("storage"));
};

// limpa autenticação
export const clearAuth = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("role");
  localStorage.removeItem("nome");
  window.dispatchEvent(new Event("storage"));
};

// lê role atual
export const getRole = () => localStorage.getItem("role");
export const getNome = () => localStorage.getItem("nome");
export const getToken = () => localStorage.getItem("token");
