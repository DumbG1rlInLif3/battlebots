package com.battlebots.controller

import com.battlebots.model.Competidor
import com.battlebots.service.CompetidorService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

/**
 * Controller para operações de Competidor
 */
@RestController
@RequestMapping("/auth/competidores")
class CompetidorController(
    private val service: CompetidorService,
    private val passwordEncoder: PasswordEncoder
) {

    @GetMapping
    fun listarTodos(): List<Competidor> {
        return service.listarTodos()
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): Competidor? {
        return service.buscarPorId(id)
    }

    @GetMapping("/email")
    fun buscarPorEmail(@RequestParam email: String): Competidor? {
        return service.buscarPorEmail(email)
    }

    // NOVO — Atualizar Competidor
    @PutMapping("/{id}")
    fun atualizar(
        @PathVariable id: Long,
        @RequestBody dados: Competidor
    ): Competidor {

        val existente = service.buscarPorId(id)
            ?: throw RuntimeException("Competidor não encontrado")

        // Atualiza apenas os campos recebidos
        existente.nome = dados.nome
        existente.email = dados.email

        // Apenas atualiza a senha se vier preenchida, e sempre criptografada
        if (!dados.senha.isNullOrBlank()) {
            existente.senha = passwordEncoder.encode(dados.senha)
        }

        return service.salvar(existente)
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long) {
        service.deletar(id)
    }
}

