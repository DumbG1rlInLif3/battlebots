package com.battlebots.controller

import com.battlebots.model.Evento
import com.battlebots.service.EventoService
import org.springframework.web.bind.annotation.*

/**
 * Controller para operações de Evento
 */
@RestController
@RequestMapping("/api/eventos")
class EventoController(private val service: EventoService) {

    @PostMapping
    fun salvar(@RequestBody evento: Evento): Evento {
        // Cria ou atualiza um evento
        return service.salvar(evento)
    }

    @GetMapping
    fun listarTodos(): List<Evento> {
        // Lista todos os eventos
        return service.listarTodos()
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): Evento? {
        // Busca evento por ID
        return service.buscarPorId(id)
    }

    @GetMapping("/nome")
    fun buscarPorNome(@RequestParam nome: String): List<Evento> {
        // Busca eventos por nome
        return service.buscarPorNome(nome)
    }

    @PutMapping("/{id}")
    fun atualizar(
        @PathVariable id: Long,
        @RequestBody eventoAtualizado: Evento
    ): Evento {
        return service.atualizar(id, eventoAtualizado)
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long) {
        // Deleta evento por ID
        service.deletar(id)
    }
}
