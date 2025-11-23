package com.battlebots.service

import com.battlebots.model.Categoria
import com.battlebots.model.Elemento
import com.battlebots.repository.CategoriaRepository
import org.springframework.stereotype.Service

@Service
class CategoriaService(private val repository: CategoriaRepository) {

    fun salvar(categoria: Categoria): Categoria {
        return repository.save(categoria)
    }

    fun listarTodas(): List<Categoria> {
        return repository.findAll()
    }

    fun buscarPorId(id: Long): Categoria? {
        return repository.findById(id).orElse(null)
    }

    /**
     * Busca categorias pelo elemento/poder (enum).
     */
    fun buscarPorPoder(poder: Elemento): List<Categoria> {
        return repository.findByPoder(poder)
    }

    fun deletar(id: Long) {
        repository.deleteById(id)
    }
}
