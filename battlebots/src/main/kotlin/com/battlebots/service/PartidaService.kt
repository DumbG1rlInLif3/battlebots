package com.battlebots.service

import com.battlebots.model.Partida
import com.battlebots.repository.PartidaRepository
import com.battlebots.repository.RoboRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class PartidaService(
    private val partidaRepository: PartidaRepository,
    private val roboRepository: RoboRepository
) {

    fun salvar(partida: Partida): Partida {
        return partidaRepository.save(partida)
    }

    fun listarTodos(): List<Partida> = partidaRepository.findAll()

    fun buscarPorId(id: Long): Partida? = partidaRepository.findById(id).orElse(null)

    fun listarPorEvento(eventoId: Long): List<Partida> = partidaRepository.findByEventoId(eventoId)

    fun definirVencedor(partidaId: Long, vencedorId: Long): Partida {
        val partida = partidaRepository.findById(partidaId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não encontrada") }

        val vencedor = roboRepository.findById(vencedorId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Robô vencedor não encontrado") }

        val perdedor = if (partida.roboA.id == vencedor.id) partida.roboB else partida.roboA

        partida.vencedor = vencedor.nome
        partida.perdedor = perdedor.nome

        return partidaRepository.save(partida)
    }

    fun deletar(id: Long) {
        if (!partidaRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não encontrada")
        }
        partidaRepository.deleteById(id)
    }
}





