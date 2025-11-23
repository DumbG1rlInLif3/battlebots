package com.battlebots.service

import com.battlebots.model.Competidor
import com.battlebots.repository.CompetidorRepository
import org.springframework.stereotype.Service

/**
 * Service para operações de Competidor
 */
@Service
class CompetidorService(private val repository: CompetidorRepository) {

    fun salvar(competidor: Competidor): Competidor {
        return repository.save(competidor)
    }

    fun listarTodos(): List<Competidor> {
        return repository.findAll()
    }

    fun buscarPorId(id: Long): Competidor? {
        return repository.findById(id).orElse(null)
    }

    fun buscarPorEmail(email: String): Competidor? {
        return repository.findByEmail(email)
    }

    fun deletar(id: Long) {
        repository.deleteById(id)
    }
}
