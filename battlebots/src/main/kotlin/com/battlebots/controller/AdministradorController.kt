package com.battlebots.controller

import com.battlebots.model.Administrador
import com.battlebots.service.AdministradorService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/administradores")
class AdministradorController(private val administradorService: AdministradorService) {

    @GetMapping
    fun listarTodos(): List<Administrador> {
        return administradorService.listarTodos()
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): Administrador? {
        return administradorService.buscarPorId(id)
    }

    @GetMapping("/email")
    fun buscarPorEmail(@RequestParam email: String): Administrador? {
        return administradorService.buscarPorEmail(email)
    }

    @PutMapping("/{id}")
    fun atualizar(
        @PathVariable id: Long,
        @RequestBody adminAtualizado: Administrador
    ): Administrador {
        return administradorService.atualizar(id, adminAtualizado)
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long) {
        administradorService.deletar(id)
    }
}

