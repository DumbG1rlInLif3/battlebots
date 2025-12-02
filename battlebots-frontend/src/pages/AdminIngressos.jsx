// Página de administração: listar todos ingressos e permitir exclusão (ADMIN)
import React, { useEffect, useState } from "react";
import { listarTodosIngressos, deletarIngresso } from "../services/ingressoService";
import { getRole } from "../services/authService";
import Button from "../components/Button";

function AdminIngressos() {
  const [ingressos, setIngressos] = useState([]);
  const [loading, setLoading] = useState(false);

  const carregar = async () => {
    setLoading(true);

    try {
      const res = await listarTodosIngressos();

      console.log("📌 Resposta completa da API de ingressos:", res.data);

      let lista = [];

      // ➤ Caso 1: backend retorna array
      if (Array.isArray(res.data)) {
        lista = res.data;
      }

      // ➤ Caso 2: backend retorna objeto com lista dentro
      else if (res.data?.content) {
        lista = res.data.content;
      }

      // ➤ Caso 3: backend retorna objeto com outro nome
      else if (res.data?.ingressos) {
        lista = res.data.ingressos;
      }

      // ➤ Garantir que nunca dê undefined
      setIngressos(lista || []);
    } catch (err) {
      console.error("❌ Erro ao listar ingressos:", err);
      alert("Erro ao listar ingressos");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    carregar();
  }, []);

  // Bloqueio de função
  if (getRole() !== "ADMINISTRADOR") {
    return (
      <main style={{ maxWidth: 700, margin: "24px auto" }}>
        <h3>Acesso negado. Apenas ADMINISTRADOR pode acessar.</h3>
      </main>
    );
  }

  const handleDelete = async (id) => {
    if (!window.confirm("Confirmar exclusão?")) return;

    try {
      await deletarIngresso(id);
      alert("Ingresso excluído");
      carregar();
    } catch (err) {
      console.error("❌ Erro ao excluir:", err);
      alert("Erro ao excluir ingresso");
    }
  };

  return (
    <main style={{ maxWidth: 1100, margin: "20px auto", padding: 12 }}>
      <h2>Ingressos (Admin)</h2>

      {loading && <div>Carregando...</div>}

      {/* Nenhum ingresso encontrado */}
      {!loading && ingressos.length === 0 && (
        <p style={{ marginTop: 20 }}>Nenhum ingresso encontrado.</p>
      )}

      {ingressos.length > 0 && (
        <table style={{ width: "100%", borderCollapse: "collapse", marginTop: 20 }}>
          <thead>
            <tr style={{ textAlign: "left", borderBottom: "1px solid #eee" }}>
              <th>ID</th>
              <th>Evento</th>
              <th>Usuário</th>
              <th>Preço</th>
              <th>Vendido</th>
              <th>Ações</th>
            </tr>
          </thead>

          <tbody>
            {ingressos.map((i) => (
              <tr key={i.id} style={{ borderBottom: "1px solid #fafafa" }}>
                <td style={{ padding: 8 }}>{i.id}</td>
                <td style={{ padding: 8 }}>{i.evento?.id ?? "-"}</td>
                <td style={{ padding: 8 }}>{i.usuario?.id ?? "-"}</td>
                <td style={{ padding: 8 }}>R$ {Number(i.preco).toFixed(2)}</td>
                <td style={{ padding: 8 }}>{i.vendido ? "Sim" : "Não"}</td>
                <td style={{ padding: 8 }}>
                  <Button onClick={() => handleDelete(i.id)}>Excluir</Button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </main>
  );
}

export default AdminIngressos;

