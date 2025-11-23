package com.battlebots.controller

import com.battlebots.model.Usuario
import com.battlebots.service.UsuarioService
import org.springframework.web.bind.annotation.*

/**
 * Controller APENAS para consulta e remoção.
 * Criação e atualização são responsabilidade do AuthController.
 */
@RestController
@RequestMapping("/auth/usuarios")
class UsuarioController(private val usuarioService: UsuarioService) {

    @GetMapping
    fun listarTodos(): List<Usuario> {
        return usuarioService.listarTodos()
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): Usuario? {
        return usuarioService.buscarPorId(id)
    }

    @GetMapping("/email")
    fun buscarPorEmail(@RequestParam email: String): Usuario? {
        return usuarioService.buscarPorEmail(email)
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long) {
        usuarioService.deletar(id)
    }
}

