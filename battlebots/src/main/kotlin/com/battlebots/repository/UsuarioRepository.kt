package com.battlebots.repository

import com.battlebots.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repositório para a entidade Usuario
 * Fornece operações CRUD automáticas pelo Spring Data JPA
 */
@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {
    // Consulta por email (útil para login/autenticação)
    fun findByEmail(email: String): Usuario?


}


