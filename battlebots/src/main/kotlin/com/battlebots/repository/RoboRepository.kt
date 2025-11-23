package com.battlebots.repository

import com.battlebots.model.Robo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoboRepository : JpaRepository<Robo, Long> {

    // Busca robôs pelo nome (insensível a maiúsculas/minúsculas)
    fun findByNomeContainingIgnoreCase(nome: String): List<Robo>

    // Lista todos os robôs de um determinado competidor
    fun findByProprietarioId(proprietarioId: Long): List<Robo>

    // Lista todos os robôs cadastrados em um evento
    fun findByEventoId(eventoId: Long): List<Robo>
}


