// App.jsx
import React, { useState } from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Navbar from "./components/Navbar";
import Home from "./pages/Home";
import Login from "./pages/Login";
import ComprarIngresso from "./pages/ComprarIngresso";
import MeusIngressos from "./pages/MeusIngressos";
import AdminIngressos from "./pages/AdminIngressos";
import { getRole, getToken } from "./services/authService";

function App() {
  // Estado global de autenticação
  const [auth, setAuth] = useState({
    token: getToken(),
    role: getRole(),
  });

  const Protected = ({ children, allowedRoles = null }) => {
    if (!auth.token) return <Navigate to="/login" replace />;
    if (allowedRoles && !allowedRoles.includes(auth.role)) return <Navigate to="/" replace />;
    return children;
  };

  return (
    <BrowserRouter>
      <Navbar auth={auth} setAuth={setAuth} />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login setAuth={setAuth} />} />

        <Route
          path="/comprar"
          element={
            <Protected allowedRoles={["USER"]}>
              <ComprarIngresso />
            </Protected>
          }
        />
        <Route
          path="/meus"
          element={
            <Protected allowedRoles={["USER"]}>
              <MeusIngressos />
            </Protected>
          }
        />
        <Route
          path="/admin/ingressos"
          element={
            <Protected allowedRoles={["ADMINISTRADOR"]}>
              <AdminIngressos />
            </Protected>
          }
        />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;




