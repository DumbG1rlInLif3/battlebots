package com.battlebots.model

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

/**
 * Subclasse de Usuario para administradores.
 * Definimos a role como "ADMINISTRADOR" (sem prefixo).
 */
@Entity
@DiscriminatorValue("ADMINISTRADOR")
class Administrador(
    nome: String = "",
    email: String = "",
    senha: String = "",
) : Usuario(
    nome = nome,
    email = email,
    senha = senha,
    role = "ADMINISTRADOR" // PADRÃO: sem "ROLE_"
)

//Essa classe usa o mesmo banco da tabela usuario, diferenciando-se apenas pelo valor da coluna tipo_usuario.
