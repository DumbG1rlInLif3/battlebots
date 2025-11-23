package com.battlebots.service

import com.battlebots.model.Robo
import com.battlebots.model.Categoria
import com.battlebots.repository.RoboRepository
import org.springframework.stereotype.Service

@Service
class RoboService(private val repository: RoboRepository) {

    fun listarTodos(): List<Robo> = repository.findAll()

    fun buscarPorId(id: Long): Robo =
        repository.findById(id).orElseThrow { RuntimeException("Robô não encontrado") }

    fun buscarPorNome(nome: String): List<Robo> =
        repository.findByNomeContainingIgnoreCase(nome)

    fun buscarPorProprietario(proprietarioId: Long): List<Robo> =
        repository.findByProprietarioId(proprietarioId)

    fun salvar(robo: Robo): Robo = repository.save(robo)

    fun atualizar(id: Long, roboAtualizado: Robo): Robo {
        val existente = repository.findById(id).orElseThrow { RuntimeException("Robô não encontrado") }
        val atualizado = existente.copy(
            nome = roboAtualizado.nome,
            descricao = roboAtualizado.descricao,
            categoria = roboAtualizado.categoria,
            proprietario = roboAtualizado.proprietario,
            evento = roboAtualizado.evento
        )
        return repository.save(atualizado)
    }

    fun deletar(id: Long) {
        if (repository.existsById(id)) repository.deleteById(id)
        else throw RuntimeException("Robô não encontrado")
    }
}


