package com.battlebots.controller

import com.battlebots.model.Usuario
import com.battlebots.model.Competidor
import com.battlebots.model.Administrador
import com.battlebots.repository.UsuarioRepository
import com.battlebots.security.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

data class LoginRequest(val email: String, val senha: String)
data class AuthResponse(val token: String, val role: String, val nome: String)

@RestController
@RequestMapping("/auth")
class AuthController(
    private val usuarioRepository: UsuarioRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        val usuario = usuarioRepository.findByEmail(loginRequest.email)
            ?: return ResponseEntity.status(401).body("Usuário não encontrado")

        if (!passwordEncoder.matches(loginRequest.senha, usuario.senha)) {
            return ResponseEntity.status(401).body("Senha incorreta")
        }

        // JWT contém "ADMINISTRADOR", não "ROLE_ADMINISTRADOR"
        val token = jwtUtil.generateToken(
            usuario.email,
            usuario.role.removePrefix("ROLE_")
        )

        return ResponseEntity.ok(
            AuthResponse(
                token = token,
                role = usuario.role.removePrefix("ROLE_"),
                nome = usuario.nome
            )
        )
    }

    @PostMapping("/register")
    fun register(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        usuario.senha = passwordEncoder.encode(usuario.senha)

        usuario.role = usuario.role
            ?.removePrefix("ROLE_")
            ?.uppercase()
            ?.ifBlank { "USER" }
            ?: "USER"

        return ResponseEntity.ok(usuarioRepository.save(usuario))
    }

    @PostMapping("/register/competidor")
    fun registerCompetidor(@RequestBody competidor: Competidor): ResponseEntity<Competidor> {
        competidor.senha = passwordEncoder.encode(competidor.senha)
        competidor.role = "COMPETIDOR"
        return ResponseEntity.ok(usuarioRepository.save(competidor))
    }

    @PostMapping("/register/admin")
    fun registerAdmin(@RequestBody admin: Administrador): ResponseEntity<Administrador> {
        admin.senha = passwordEncoder.encode(admin.senha)
        admin.role = "ADMINISTRADOR"
        return ResponseEntity.ok(usuarioRepository.save(admin))
    }
}

//O token gerado vem com o role embutido
//pode enviar autorization: bearer <token> nas requisições protegidas
//o front-end poderá armazenar ele junto com o token e adaptar a interface  (exemplo: mostrar menus diferentes
//para admin/competidor

