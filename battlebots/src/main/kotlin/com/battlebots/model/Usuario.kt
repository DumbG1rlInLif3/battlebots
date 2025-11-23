package com.battlebots.model

import jakarta.persistence.*

/**
 * Entidade base para todos os tipos de usuário.
 *
 * OBS:
 *  - Aqui armazenamos `role` SEM o prefixo "ROLE_".
 *    Exemplos válidos: "ADMINISTRADOR", "COMPETIDOR", "USER"
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
open class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,

    @Column(nullable = false)
    open var nome: String = "",
    @Column(nullable = false, unique = true)
    open var email: String = "",

    @Column(nullable = false)
    open var senha: String = "",

    /**
     * role gravada no DB SEM o prefixo ROLE_ (Spring adiciona o prefixo quando necessário).
     */
    @Column(nullable = false)
    open var role: String = "USER"
)


//open class → Necessário no Kotlin para permitir herança.
//open var → Permite sobrescrever nas subclasses.
//@Inheritance e @DiscriminatorColumn → Informam ao Hibernate que usaremos uma tabela única (SINGLE_TABLE) para todos os tipos de usuário.
