package com.battlebots.repository

import com.battlebots.model.Competidor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repositório específico para Competidores
 */
@Repository
interface CompetidorRepository : JpaRepository<Competidor, Long> {
    fun findByEmail(email: String): Competidor?
}
