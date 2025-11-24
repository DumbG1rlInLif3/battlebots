package com.battlebots.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                // LOGIN e REGISTRO liberados
                auth.requestMatchers("/auth/**").permitAll()

                // USER pode comprar ingresso
                auth.requestMatchers(HttpMethod.POST, "/api/ingressos/comprar")
                    .hasAnyRole("USER", "COMPETIDOR", "ADMINISTRADOR")

                // ROTAS ADMINISTRATIVAS
                auth.requestMatchers("/api/categorias/**", "/api/eventos/**", "/api/partidas/**")
                    .hasRole("ADMINISTRADOR")

                // Listar ingressos próprios
                auth.requestMatchers(HttpMethod.GET, "/api/ingressos/meus/**")
                    .hasAnyRole("USER", "ADMINISTRADOR")

                // Listar ingressos de eventos
                auth.requestMatchers(HttpMethod.GET, "/api/ingressos/evento/**")
                    .hasAnyRole("USER", "ADMINISTRADOR")

                // Rotas para COMPETIDOR e ADMIN
                auth.requestMatchers("/api/robos/**")
                    .hasAnyRole("COMPETIDOR", "ADMINISTRADOR")

                // Qualquer outra requisição precisa estar autenticada
                auth.anyRequest().authenticated()
            }
            // Filtro JWT para autenticação stateless
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}







