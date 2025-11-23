package com.battlebots.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.time.LocalDateTime
import com.fasterxml.jackson.annotation.JsonFormat

@Entity
@JsonIgnoreProperties("inscritos")
data class Evento(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val nome: String,

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // <-- Adicione esta linha
    val data: LocalDateTime,

    val local: String,
    val descricao: String,

    @ManyToOne
    val categoria: Categoria,

    var capacidade: Int = 0,
    var ingressosDisponiveis: Int = 0,

    @OneToMany(mappedBy = "evento", cascade = [CascadeType.ALL])
    var inscritos: MutableList<Robo> = mutableListOf(),

)


