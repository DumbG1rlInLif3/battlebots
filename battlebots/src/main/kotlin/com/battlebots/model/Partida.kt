package com.battlebots.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
@Table(name = "partidas")
data class Partida(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id", nullable = false)
    @JsonIgnoreProperties("partidas", "hibernateLazyInitializer", "handler")
    val evento: Evento,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "robo_a_id", nullable = false)
    @JsonIgnoreProperties("partidas", "hibernateLazyInitializer", "handler")
    val roboA: Robo,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "robo_b_id", nullable = false)
    @JsonIgnoreProperties("partidas", "hibernateLazyInitializer", "handler")
    val roboB: Robo,

    @Column(nullable = false)
    val placarA: Int,

    @Column(nullable = false)
    val placarB: Int,

    @Column(nullable = false)
    val horario: String,

    var vencedor: String? = null,
    var perdedor: String? = null
)






