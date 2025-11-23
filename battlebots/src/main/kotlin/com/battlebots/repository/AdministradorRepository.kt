package com.battlebots.repository

import com.battlebots.model.Administrador
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdministradorRepository : JpaRepository<Administrador, Long> {
    fun findByEmail(email: String): Administrador?
}
