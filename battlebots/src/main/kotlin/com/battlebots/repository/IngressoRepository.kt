package com.battlebots.repository

import com.battlebots.model.Ingresso
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repositório para ingressos
 */
@Repository
interface IngressoRepository : JpaRepository<Ingresso, Long> {
    // Buscar ingressos de um usuário específico
    fun findByUsuarioId(usuarioId: Long): List<Ingresso>

    // Buscar ingressos de um evento específico
    fun findByEventoId(eventoId: Long): List<Ingresso>
}
