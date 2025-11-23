package com.battlebots.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
@Table(name = "robo")
@JsonIgnoreProperties(value = ["hibernateLazyInitializer", "handler"])
data class Robo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val nome: String = "",

    val descricao: String = "",

    // ✅ categoria agora define o poder
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties(value = ["robos", "hibernateLazyInitializer", "handler"])
    val categoria: Categoria,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprietario_id")
    @JsonIgnoreProperties(value = ["robos", "hibernateLazyInitializer", "handler"])
    val proprietario: Competidor? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    @JsonIgnoreProperties(value = ["robos", "hibernateLazyInitializer", "handler"])
    val evento: Evento? = null
)


