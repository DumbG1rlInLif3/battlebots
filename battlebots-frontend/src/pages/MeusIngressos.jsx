// Lista os ingressos do usuário (GET /api/ingressos/meus/{usuarioId})
import React, { useEffect, useState } from "react";
import { listarMeusIngressos } from "../services/ingressoService";
import { getRole } from "../services/authService";
import Button from "../components/Button";

function MeusIngressos() {
  const [ingressos, setIngressos] = useState([]);
  const [loading, setLoading] = useState(false);
  const [usuarioId, setUsuarioId] = useState("");

  useEffect(() => {
    // não carrega automaticamente, usuário precisa informar seu id (poderíamos pegar no login)
  }, []);

  const carregar = async () => {
    setLoading(true);
    try {
      const res = await listarMeusIngressos(Number(usuarioId));
      setIngressos(res.data || []);
    } catch (err) {
      console.error(err);
      alert("Erro ao carregar ingressos");
    } finally {
      setLoading(false);
    }
  };

  if (getRole() !== "USER") {
    return <main style={{ maxWidth: 700, margin: "24px auto" }}><h3>Acesso negado. Apenas usuários (USER) podem ver seus ingressos.</h3></main>;
  }

  return (
    <main style={{ maxWidth: 900, margin: "18px auto", padding: 12 }}>
      <h2>Meus Ingressos</h2>

      <div style={{ marginBottom: 12, display: "flex", gap: 8 }}>
        <input placeholder="Digite seu ID" value={usuarioId} onChange={(e) => setUsuarioId(e.target.value)} style={{ padding: 8, borderRadius: 6, border: "1px solid #ddd" }} />
        <Button onClick={carregar}>Carregar</Button>
      </div>

      {loading && <div>Carregando...</div>}

      {ingressos.length === 0 && <p>Nenhum ingresso encontrado.</p>}

      <div style={{ display: "grid", gap: 8 }}>
        {ingressos.map(i => (
          <div key={i.id} style={{ background: "#fff", padding: 12, borderRadius: 8, boxShadow: "0 1px 3px rgba(0,0,0,0.06)" }}>
            <div><strong>Ingresso #{i.id}</strong></div>
            <div>Evento: {i.evento?.id ?? "—"}</div>
            <div>Preço: R$ {Number(i.preco).toFixed(2)}</div>
            <div>Vendido: {i.vendido ? "Sim" : "Não"}</div>
          </div>
        ))}
      </div>
    </main>
  );
}

export default MeusIngressos;
