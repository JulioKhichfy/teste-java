package com.gerenciador.pedidos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API com Java 17 e Spring Boot 3 para Gerenciar Lista de Pedidos")
                        .version("v1")
                        .description("Upload de arquivo JSON ou XML de Pedidos e consultas por número de pedido, por data e todos")
                        .termsOfService("")
                        .license(
                                new License()
                                        .name("julio cesar khichfy")
                                        .url("https://github.com/JulioKhichfy/teste-java")
                        )
                );
    }

}