// Navbar.jsx
import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { clearAuth } from "../services/authService";

function Navbar({ auth, setAuth }) {
  const navigate = useNavigate();

  const handleLogout = () => {
    clearAuth();
    setAuth({ token: null, role: null });
    navigate("/login");
  };

  return (
    <header style={{ background: "#0f1724", color: "#fff", padding: 12 }}>
      <div style={{ maxWidth: 1100, margin: "0 auto", display: "flex", justifyContent: "space-between", alignItems: "center" }}>
        <div style={{ fontWeight: 700 }}>BattleBots</div>
        <nav style={{ display: "flex", gap: 12, alignItems: "center" }}>
          <Link to="/" style={{ color: "#cfe3ff", textDecoration: "none" }}>Home</Link>

          {auth.role === "USER" && <Link to="/comprar" style={{ color: "#cfe3ff", textDecoration: "none" }}>Comprar</Link>}
          {auth.role === "USER" && <Link to="/meus" style={{ color: "#cfe3ff", textDecoration: "none" }}>Meus Ingressos</Link>}

          {auth.role === "ADMINISTRADOR" && <Link to="/admin/ingressos" style={{ color: "#cfe3ff", textDecoration: "none" }}>Ingressos (Admin)</Link>}

          {!auth.token ? (
            <Link to="/login" style={{ color: "#cfe3ff", textDecoration: "none" }}>Entrar</Link>
          ) : (
            <>
              <span style={{ color: "#9fbff5" }}>{localStorage.getItem("nome")} ({auth.role})</span>
              <button onClick={handleLogout} style={{ background: "transparent", color: "#fff", border: "1px solid rgba(255,255,255,0.08)", padding: "6px 8px", borderRadius: 6 }}>
                Sair
              </button>
            </>
          )}
        </nav>
      </div>
    </header>
  );
}

export default Navbar;





