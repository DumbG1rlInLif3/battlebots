package com.battlebots.controller

import com.battlebots.model.Partida
import com.battlebots.repository.EventoRepository
import com.battlebots.repository.RoboRepository
import com.battlebots.service.PartidaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

@RestController
@RequestMapping("/api/partidas")
class PartidaController(
    private val partidaService: PartidaService,
    private val eventoRepository: EventoRepository,
    private val roboRepository: RoboRepository
) {

    // Criar nova partida
    @PostMapping
    fun criarPartida(@RequestBody dto: PartidaDTO): ResponseEntity<Partida> {
        val evento = eventoRepository.findById(dto.eventoId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado") }

        val roboA = roboRepository.findById(dto.roboAId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Robô A não encontrado") }

        val roboB = roboRepository.findById(dto.roboBId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Robô B não encontrado") }

        val partida = Partida(
            evento = evento,
            roboA = roboA,
            roboB = roboB,
            placarA = dto.placarA,
            placarB = dto.placarB,
            horario = dto.horario,
            vencedor = dto.vencedor,
            perdedor = dto.perdedor
        )

        val salva = partidaService.salvar(partida)
        return ResponseEntity.status(HttpStatus.CREATED).body(salva)
    }

    // Listar todas as partidas
    @GetMapping
    fun listarPartidas(): List<Partida> = partidaService.listarTodos()

    // Buscar partida por ID
    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): ResponseEntity<Partida> {
        val partida = partidaService.buscarPorId(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não encontrada")
        return ResponseEntity.ok(partida)
    }

    // Buscar partidas de um evento específico
    @GetMapping("/evento/{eventoId}")
    fun listarPorEvento(@PathVariable eventoId: Long): List<Partida> {
        return partidaService.listarPorEvento(eventoId)
    }

    // Atualizar vencedor
    @PutMapping("/{id}/vencedor/{vencedorId}")
    fun definirVencedor(@PathVariable id: Long, @PathVariable vencedorId: Long): ResponseEntity<Partida> {
        val partida = partidaService.definirVencedor(id, vencedorId)
        return ResponseEntity.ok(partida)
    }

    // Deletar partida
    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long): ResponseEntity<Void> {
        partidaService.deletar(id)
        return ResponseEntity.noContent().build()
    }
}

/**
 * DTO utilizado para criação e atualização de partidas.
 * Agora compatível com Jackson (usa @JsonCreator e @JsonProperty).
 */
data class PartidaDTO @JsonCreator constructor(
    @JsonProperty("eventoId") val eventoId: Long,
    @JsonProperty("roboAId") val roboAId: Long,
    @JsonProperty("roboBId") val roboBId: Long,
    @JsonProperty("placarA") val placarA: Int,
    @JsonProperty("placarB") val placarB: Int,
    @JsonProperty("horario") val horario: String,
    @JsonProperty("vencedor") val vencedor: String? = null,
    @JsonProperty("perdedor") val perdedor: String? = null
)











