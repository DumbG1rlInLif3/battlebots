package com.battlebots.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

/**
 * Representa uma categoria de combate de robôs.
 */
@Entity
@JsonIgnoreProperties("eventos")
data class Categoria(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    // poder/elemento
    @Enumerated(EnumType.STRING)
    val poder: Elemento, // obrigatório

    @OneToMany(mappedBy = "categoria", cascade = [CascadeType.ALL])
    @JsonIgnoreProperties("categoria")
    val eventos: List<Evento> = mutableListOf()
)