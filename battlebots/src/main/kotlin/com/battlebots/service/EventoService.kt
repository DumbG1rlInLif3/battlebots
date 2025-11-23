package com.battlebots.service

import com.battlebots.model.Evento
import com.battlebots.repository.EventoRepository
import org.springframework.stereotype.Service

/**
 * Service para operações de Evento
 */
@Service
class EventoService(private val repository: EventoRepository) {

    fun salvar(evento: Evento): Evento {
        return repository.save(evento)
    }

    fun listarTodos(): List<Evento> {
        return repository.findAll()
    }

    fun buscarPorId(id: Long): Evento? {
        return repository.findById(id).orElse(null)
    }

    fun buscarPorNome(nome: String): List<Evento> {
        return repository.findByNomeContainingIgnoreCase(nome)
    }

    fun atualizar(id: Long, novo: Evento): Evento {
        val existente = repository.findById(id).orElseThrow {
            RuntimeException("Evento não encontrado")
        }

        val atualizado = existente.copy(
            nome = novo.nome,
            data = novo.data,
            local = novo.local,
            descricao = novo.descricao,
            categoria = novo.categoria,
            ingressosDisponiveis = novo.ingressosDisponiveis,
            inscritos = existente.inscritos // não substitui inscritos pelo JSON
        )

        return repository.save(atualizado)
    }


    fun deletar(id: Long) {
        repository.deleteById(id)
    }
}
