package com.battlebots.model

import jakarta.persistence.*

@Entity
data class Ingresso(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    val evento: Evento,

    @ManyToOne
    val usuario: Usuario,

    val preco: Double,
    var vendido: Boolean = true
)

