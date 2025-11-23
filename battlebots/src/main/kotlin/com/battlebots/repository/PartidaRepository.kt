package com.battlebots.repository

import com.battlebots.model.Partida
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PartidaRepository : JpaRepository<Partida, Long> {
    fun findByEventoId(eventoId: Long): List<Partida>
}

