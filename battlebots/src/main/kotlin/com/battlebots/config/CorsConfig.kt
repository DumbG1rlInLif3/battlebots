// Define o pacote onde esta classe está localizada
// Assim o Spring sabe onde encontrá-la ao inicializar o projeto
package com.battlebots.config

// Importa as classes necessárias do Spring para criar uma configuração
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

// A anotação @Configuration diz ao Spring que esta classe contém definições
// de configuração que devem ser carregadas quando o aplicativo iniciar.
@Configuration
class CorsConfig {

    // @Bean indica que o método retorna um objeto que o Spring deve gerenciar.
    // Neste caso, ele cria e registra uma configuração personalizada de CORS.
    @Bean
    fun corsConfigurer(): WebMvcConfigurer {

        // Retorna uma implementação anônima de WebMvcConfigurer
        // Essa interface permite modificar as configurações padrão do Spring MVC.
        return object : WebMvcConfigurer {

            // Sobrescrevemos o método addCorsMappings, que define regras de CORS.
            override fun addCorsMappings(registry: CorsRegistry) {

                // registry.addMapping("/**") -> aplica a configuração para todas as rotas do sistema
                registry.addMapping("/**")

                    // allowedOrigins -> define quais endereços podem fazer requisições à API.
                    // No caso, o React roda em "http://localhost:5173"
                    .allowedOrigins("http://localhost:5173")

                    // allowedMethods -> define quais métodos HTTP são permitidos
                    // (GET para buscar, POST para enviar, PUT para atualizar, DELETE para remover)
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

                    // allowedHeaders -> permite todos os tipos de cabeçalhos HTTP
                    .allowedHeaders("*")

                    // allowCredentials -> permite envio de cookies e autenticação junto das requisições
                    .allowCredentials(true)
            }
        }
    }
}
