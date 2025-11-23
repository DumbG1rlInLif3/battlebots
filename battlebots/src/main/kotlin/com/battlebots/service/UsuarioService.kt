package com.battlebots.service

import com.battlebots.model.Usuario
import com.battlebots.repository.UsuarioRepository
import org.springframework.stereotype.Service

/**
 * Service para operações relacionadas a Usuário
 */
@Service
class UsuarioService(private val usuarioRepository: UsuarioRepository) {

    fun salvar(usuario: Usuario): Usuario {
        // Salva ou atualiza um usuário
        return usuarioRepository.save(usuario)
    }

    fun listarTodos(): List<Usuario> {
        // Retorna todos os usuários
        return usuarioRepository.findAll()
    }

    fun buscarPorId(id: Long): Usuario? {
        // Busca usuário por ID
        return usuarioRepository.findById(id).orElse(null)
    }

    fun buscarPorEmail(email: String): Usuario? {
        // Busca usuário pelo email (útil para login)
        return usuarioRepository.findByEmail(email)
    }

    fun deletar(id: Long) {
        // Deleta usuário pelo ID
        usuarioRepository.deleteById(id)
    }
}
