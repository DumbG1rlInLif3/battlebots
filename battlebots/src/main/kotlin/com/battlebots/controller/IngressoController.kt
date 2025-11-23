package com.battlebots.controller

import com.battlebots.model.Ingresso
import com.battlebots.service.EventoService
import com.battlebots.service.IngressoService
import com.battlebots.service.UsuarioService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import com.battlebots.repository.IngressoRepository
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/ingressos")
class IngressoController(
    private val ingressoService: IngressoService,
    private val usuarioService: UsuarioService,
    private val eventoService: EventoService,
    private val ingressoRepository: IngressoRepository
) {

    // DTO usado para compra de ingresso
    data class CompraIngressoDTO(
        val eventoId: Long,
        val usuarioId: Long,
        val preco: Double
    )

    // ------------------------
    // COMPRA (USUÁRIO NORMAL)
    // ------------------------
    @PostMapping("/comprar")
    @PreAuthorize("hasRole('USER')")
    fun comprarIngresso(@RequestBody dto: CompraIngressoDTO): Ingresso {

        val usuario = usuarioService.buscarPorId(dto.usuarioId)
            ?: throw Exception("Usuário não encontrado")

        val evento = eventoService.buscarPorId(dto.eventoId)
            ?: throw Exception("Evento não encontrado")

        // -------------------------------
        // REGRA 1 → Já comprou ingresso?
        // -------------------------------
        val ingressosDoUsuario = ingressoRepository.findByUsuarioId(dto.usuarioId)

        val jaComprou = ingressosDoUsuario.any { it.evento.id == dto.eventoId }
        if (jaComprou) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já comprou ingresso para este evento")
        }

        // -------------------------------------
        // REGRA 2 → Evento tem vagas disponíveis?
        // -------------------------------------
        if (evento.ingressosDisponiveis <= 0)
            throw Exception("Não há mais ingressos disponíveis.")

        // -------------------------------------
        // REGRA 3 → Criar e salvar ingresso
        // -------------------------------------
        val ingresso = Ingresso(
            evento = evento,
            usuario = usuario,
            preco = dto.preco,
            vendido = true
        )

        // Atualiza contagem do evento
        evento.ingressosDisponiveis -= 1
        eventoService.salvar(evento)

        return ingressoService.salvar(ingresso)
    }

    // ------------------------
    // LISTAR MEUS INGRESSOS
    // ------------------------
    @GetMapping("/meus/{usuarioId}")
    @PreAuthorize("hasRole('USER')")
    fun listarMeusIngressos(@PathVariable usuarioId: Long): List<Ingresso> {
        return ingressoService.listarPorUsuario(usuarioId)
    }

    // ------------------------
    // (NOVO) LISTAR INGRESSOS DE UM EVENTO
    // ------------------------
    @GetMapping("/eventos/{eventoId}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USER')")
    fun listarPorEvento(@PathVariable eventoId: Long): List<Ingresso> {
        return ingressoService.listarPorEvento(eventoId)
    }

    // ------------------------
    // ADMIN – LISTAR TODOS
    // ------------------------
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    fun listarTodos(): List<Ingresso> {
        return ingressoService.listarTodos()
    }

    // ------------------------
    // ADMIN – DELETAR
    // ------------------------
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    fun deletar(@PathVariable id: Long) {
        ingressoService.deletar(id)
    }
}



