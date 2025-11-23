package com.battlebots.service

import com.battlebots.model.Administrador
import com.battlebots.repository.AdministradorRepository
import org.springframework.stereotype.Service

@Service
class AdministradorService(private val administradorRepository: AdministradorRepository) {

    fun salvar(admin: Administrador): Administrador {
        return administradorRepository.save(admin)
    }

    fun listarTodos(): List<Administrador> {
        return administradorRepository.findAll()
    }

    fun buscarPorId(id: Long): Administrador? {
        return administradorRepository.findById(id).orElse(null)
    }

    fun buscarPorEmail(email: String): Administrador? {
        return administradorRepository.findByEmail(email)
    }

    fun atualizar(id: Long, adminAtualizado: Administrador): Administrador {
        val existente = administradorRepository.findById(id)
            .orElseThrow { RuntimeException("Administrador não encontrado") }

        // Atualizando manualmente os campos permitidos
        existente.nome = adminAtualizado.nome
        existente.email = adminAtualizado.email
        existente.senha = adminAtualizado.senha

        return administradorRepository.save(existente)
    }


    fun deletar(id: Long) {
        administradorRepository.deleteById(id)
    }
}


