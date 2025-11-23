package com.battlebots.controller

import com.battlebots.model.Categoria
import com.battlebots.model.Elemento
import com.battlebots.service.CategoriaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categorias")
class CategoriaController(private val service: CategoriaService) {

    /**
     * Cria uma nova categoria com um poder (Elemento)
     */
    @PostMapping
    fun criarCategoria(@RequestBody categoria: Categoria): ResponseEntity<Categoria> {
        val novaCategoria = service.salvar(categoria)
        return ResponseEntity.ok(novaCategoria)
    }

    /**
     * Lista todas as categorias existentes
     */
    @GetMapping
    fun listarCategorias(): ResponseEntity<List<Categoria>> {
        return ResponseEntity.ok(service.listarTodas())
    }

    /**
     * Busca uma categoria específica pelo ID
     */
    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): ResponseEntity<Categoria> {
        val categoria = service.buscarPorId(id)
        return if (categoria != null) ResponseEntity.ok(categoria)
        else ResponseEntity.notFound().build()
    }

    /**
     * Busca categorias por PODER (Elemento)
     * Exemplo: /api/categorias/buscar?poder=Fogo
     */
    @GetMapping("/buscar")
    fun buscarPorPoder(@RequestParam poder: Elemento): ResponseEntity<List<Categoria>> {
        return ResponseEntity.ok(service.buscarPorPoder(poder))
    }

    /**
     * Atualiza uma categoria pelo ID
     */
    @PutMapping("/{id}")
    fun atualizar(
        @PathVariable id: Long,
        @RequestBody categoriaAtualizada: Categoria
    ): ResponseEntity<Categoria> {

        val categoriaExistente = service.buscarPorId(id)

        return if (categoriaExistente != null) {
            val categoriaSalva = service.salvar(
                categoriaAtualizada.copy(id = id)
            )
            ResponseEntity.ok(categoriaSalva)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * Exclui uma categoria
     */
    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long): ResponseEntity<Void> {
        service.deletar(id)
        return ResponseEntity.noContent().build()
    }
}
