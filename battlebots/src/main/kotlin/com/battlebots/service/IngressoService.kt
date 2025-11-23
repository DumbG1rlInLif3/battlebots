package com.battlebots.service

import com.battlebots.model.Ingresso
import com.battlebots.repository.IngressoRepository
import org.springframework.stereotype.Service

/**
 * Service para operações de Ingresso
 */
@Service
class IngressoService(private val repository: IngressoRepository) {

    fun salvar(ingresso: Ingresso): Ingresso {
        return repository.save(ingresso)
    }

    fun listarTodos(): List<Ingresso> {
        return repository.findAll()
    }

    fun buscarPorId(id: Long): Ingresso? {
        return repository.findById(id).orElse(null)
    }

    fun listarPorUsuario(usuarioId: Long): List<Ingresso> {
        return repository.findByUsuarioId(usuarioId)
    }

    fun listarPorEvento(eventoId: Long): List<Ingresso> {
        return repository.findByEventoId(eventoId)
    }

    fun deletar(id: Long) {
        repository.deleteById(id)
    }
}
