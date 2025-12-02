// Página para o USER comprar ingresso (POST /api/ingressos/comprar)
import React, { useState } from "react";
import { comprarIngresso } from "../services/ingressoService";
import Input from "../components/Input";
import Button from "../components/Button";
import { getRole, getNome } from "../services/authService";

function ComprarIngresso() {
  const [eventoId, setEventoId] = useState("");
  const [usuarioId, setUsuarioId] = useState("");
  const [preco, setPreco] = useState("");
  const [loading, setLoading] = useState(false);
  const [msg, setMsg] = useState(null);

  // opcional: preenche usuário com id salvo se você tem essa info localmente
  // neste exemplo pedimos que usuário informe seu id (ou armazene no login)
  const handleSubmit = async (e) => {
    e.preventDefault();
    setMsg(null);

    try {
      setLoading(true);
      await comprarIngresso({
        eventoId: Number(eventoId),
        usuarioId: Number(usuarioId),
        preco: Number(preco)
      });
      setMsg({ type: "success", text: "Ingresso comprado com sucesso!" });
      // limpa campos
      setEventoId(""); setUsuarioId(""); setPreco("");
    } catch (err) {
      console.error(err);
      const text = err?.response?.data || "Erro ao comprar ingresso";
      setMsg({ type: "error", text: String(text) });
    } finally {
      setLoading(false);
    }
  };

  // bloqueia acesso se role não for USER
  if (getRole() !== "USER") {
    return <main style={{ maxWidth: 700, margin: "24px auto" }}><h3>Acesso negado. Apenas usuários (USER) podem comprar ingressos.</h3></main>;
  }

  return (
    <main style={{ maxWidth: 700, margin: "18px auto", padding: 12 }}>
      <h2>Comprar Ingresso</h2>

      {msg && <div style={{ marginBottom: 12, color: msg.type === "error" ? "crimson" : "green" }}>{msg.text}</div>}

      <form onSubmit={handleSubmit}>
        <Input label="ID do Evento" name="eventoId" value={eventoId} onChange={(e) => setEventoId(e.target.value)} placeholder="Ex: 1" />
        <Input label="Seu ID de Usuário" name="usuarioId" value={usuarioId} onChange={(e) => setUsuarioId(e.target.value)} placeholder="Ex: 2" />
        <Input label="Preço" name="preco" value={preco} onChange={(e) => setPreco(e.target.value)} type="number" placeholder="Ex: 50.00" />

        <div style={{ display: "flex", gap: 8 }}>
          <Button type="submit" disabled={loading}>{loading ? "Comprando..." : "Comprar"}</Button>
        </div>
      </form>
    </main>
  );
}

export default ComprarIngresso;
