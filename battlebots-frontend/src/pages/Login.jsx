// Login.jsx
// Aqui precisamos atualizar o estado global auth após login
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login as authServiceLogin, saveAuth } from "../services/authService";
import Input from "../components/Input";
import Button from "../components/Button";

function Login({ setAuth }) {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [loading, setLoading] = useState(false);
  const [alert, setAlert] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setAlert(null);

    try {
      setLoading(true);
      const res = await authServiceLogin(email, senha);
      const data = res.data;

      saveAuth(data);
      setAuth({ token: data.token, role: data.role }); // Atualiza estado global
      navigate("/");
    } catch (err) {
      console.error(err);
      const msg = err?.response?.data?.message || "Erro ao autenticar";
      setAlert(msg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <main style={{ maxWidth: 600, margin: "24px auto", padding: 16 }}>
      <h2>Login</h2>
      {alert && <div style={{ marginBottom: 12, color: "crimson" }}>{String(alert)}</div>}

      <form onSubmit={handleSubmit}>
        <Input label="Email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="seu@exemplo.com" />
        <Input label="Senha" name="senha" value={senha} onChange={(e) => setSenha(e.target.value)} type="password" />

        <div style={{ display: "flex", gap: 8, marginTop: 12 }}>
          <Button type="submit" disabled={loading}>{loading ? "Entrando..." : "Entrar"}</Button>
        </div>
      </form>
    </main>
  );
}

export default Login;



