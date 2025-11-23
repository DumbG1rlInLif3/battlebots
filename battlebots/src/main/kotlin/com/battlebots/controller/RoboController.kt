package com.battlebots.controller

import com.battlebots.model.Categoria
import com.battlebots.model.Robo
import com.battlebots.repository.CompetidorRepository
import com.battlebots.repository.EventoRepository
import com.battlebots.repository.RoboRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

// DTOs
data class RoboDTO(
    val nome: String = "",
    val descricao: String = "",
    val categoriaId: Long,          // referência obrigatória à Categoria
    val proprietarioId: Long? = null,
    val eventoId: Long? = null
)

data class RoboResponseDTO(
    val id: Long,
    val nome: String,
    val descricao: String,
    val categoria: Categoria,       // inclui o objeto completo de Categoria
    val proprietarioId: Long?,
    val eventoId: Long?
)

@RestController
@RequestMapping("/api/robos")
class RoboController(
    private val roboRepository: RoboRepository,
    private val competidorRepository: CompetidorRepository,
    private val eventoRepository: EventoRepository,
    private val categoriaRepository: com.battlebots.repository.CategoriaRepository
) {

    @PostMapping
    fun criarRobo(@RequestBody dto: RoboDTO): RoboResponseDTO {
        val proprietario = dto.proprietarioId?.let {
            competidorRepository.findById(it).orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Competidor não encontrado")
            }
        }

        val evento = dto.eventoId?.let {
            eventoRepository.findById(it).orElse(null)
        }

        val categoria = categoriaRepository.findById(dto.categoriaId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada")
        }

        val robo = Robo(
            nome = dto.nome,
            descricao = dto.descricao,
            categoria = categoria,
            proprietario = proprietario,
            evento = evento
        )

        val salvo = roboRepository.save(robo)
        return salvo.toResponse()
    }

    @GetMapping
    fun listarRobos(): List<RoboResponseDTO> =
        roboRepository.findAll().map { it.toResponse() }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): RoboResponseDTO {
        val robo = roboRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Robô não encontrado")
        }
        return robo.toResponse()
    }

    @PutMapping("/{id}")
    fun atualizarRobo(@PathVariable id: Long, @RequestBody dto: RoboDTO): RoboResponseDTO {
        val roboExistente = roboRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Robô não encontrado")
        }

        val proprietario = dto.proprietarioId?.let {
            competidorRepository.findById(it).orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Competidor não encontrado")
            }
        }

        val evento = dto.eventoId?.let {
            eventoRepository.findById(it).orElse(null)
        }

        val categoria = categoriaRepository.findById(dto.categoriaId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada")
        }

        val atualizado = roboExistente.copy(
            nome = dto.nome,
            descricao = dto.descricao,
            categoria = categoria,
            proprietario = proprietario,
            evento = evento
        )

        val salvo = roboRepository.save(atualizado)
        return salvo.toResponse()
    }

    @DeleteMapping("/{id}")
    fun deletarRobo(@PathVariable id: Long) {
        if (!roboRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Robô não encontrado")
        }
        roboRepository.deleteById(id)
    }

    // Extensão para converter automaticamente Robo -> RoboResponseDTO
    private fun Robo.toResponse() = RoboResponseDTO(
        id = this.id,
        nome = this.nome,
        descricao = this.descricao,
        categoria = this.categoria,
        proprietarioId = this.proprietario?.id,
        eventoId = this.evento?.id
    )
}





