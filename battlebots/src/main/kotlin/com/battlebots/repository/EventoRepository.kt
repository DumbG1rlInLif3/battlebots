package com.battlebots.repository

import com.battlebots.model.Evento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventoRepository : JpaRepository<Evento, Long> {
    fun findByNomeContainingIgnoreCase(nome: String): List<Evento>
}
