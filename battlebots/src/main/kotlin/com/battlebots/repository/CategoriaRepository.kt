package com.battlebots.repository

import com.battlebots.model.Categoria
import com.battlebots.model.Elemento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repositório responsável pelo acesso aos dados de Categoria.
 * Oferece métodos prontos como save(), findAll(), findById(), deleteById()
 * e também consultas personalizadas.
 */
@Repository
interface CategoriaRepository : JpaRepository<Categoria, Long> {

    /**
     * Busca categorias pelo nome, ignorando diferenças entre maiúsculas e minúsculas.
     */
    fun findByPoder(poder: Elemento): List<Categoria>
}

