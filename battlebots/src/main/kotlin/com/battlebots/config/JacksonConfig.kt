package com.battlebots.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.findAndRegisterModules() // registra todos os módulos automaticamente
        mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // mostra datas como "2025-11-09T22:30:00"
        return mapper
    }
}

//O JacksonConfig serve para personalizar o comportamento do Jackson, a biblioteca
//responsável por converter objetos Kotlin ↔ JSON (entrada e saída da API).
