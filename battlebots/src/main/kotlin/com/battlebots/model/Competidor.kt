package com.battlebots.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
@DiscriminatorValue("COMPETIDOR")
@JsonIgnoreProperties("robo")
class Competidor(
    nome: String = "",
    email: String = "",
    senha: String = ""
) : Usuario(
    nome = nome,
    email = email,
    senha = senha,
    role = "COMPETIDOR" // PADRÃO: sem "ROLE_"
)


