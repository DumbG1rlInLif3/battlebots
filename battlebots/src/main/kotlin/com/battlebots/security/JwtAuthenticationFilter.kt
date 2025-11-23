package com.battlebots.security

import com.battlebots.service.CustomUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtil,
    private val customUserDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authHeader = request.getHeader("Authorization")

            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                val token = authHeader.substring(7)
                val username = jwtUtil.getUsernameFromToken(token)

                if (username != null && SecurityContextHolder.getContext().authentication == null) {
                    // Carrega user para circunstâncias onde precisamos das credenciais completas
                    val userDetails = customUserDetailsService.loadUserByUsername(username)

                    if (jwtUtil.validateToken(token)) {
                        // Preferimos a role do token (stateless). Se não houver, usamos userDetails.authorities
                        val roleFromToken = jwtUtil.getRoleFromToken(token)
                        val authorities = if (roleFromToken != null) {
                            listOf(SimpleGrantedAuthority("ROLE_${roleFromToken.uppercase()}"))
                        } else {
                            userDetails.authorities.toList()
                        }

                        val authToken = UsernamePasswordAuthenticationToken(userDetails, null, authorities)
                        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                        SecurityContextHolder.getContext().authentication = authToken
                    }
                }
            }
        } catch (ex: Exception) {
            // Em produção troque por logger.error(...)
            println("Erro ao validar JWT: ${ex.message}")
        }

        filterChain.doFilter(request, response)
    }
}


