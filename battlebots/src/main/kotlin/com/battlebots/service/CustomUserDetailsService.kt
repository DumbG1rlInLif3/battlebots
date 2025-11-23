package com.battlebots.service

import com.battlebots.repository.UsuarioRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * Carrega UserDetails a partir do banco.
 *
 * Importante:
 *  - Espera-se que usuario.role esteja armazenado SEM "ROLE_" (ex: "ADMINISTRADOR").
 *  - Usa .roles(roleClean) para que o Spring adicione "ROLE_" automaticamente.
 */
@Service
class CustomUserDetailsService(
    private val usuarioRepository: UsuarioRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val usuario = usuarioRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("Usuário não encontrado: $email")

        val roleClean = usuario.role.removePrefix("ROLE_").uppercase()

        return User.builder()
            .username(usuario.email)
            .password(usuario.senha)
            .roles(roleClean) // spring gera ROLE_${roleClean}
            .build()
    }
}


