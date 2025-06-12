package com.detyparfum.gestao.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
            .info(new Info()
                .title("API Loja Dety Parfum")
                .version("1.0.0")
                .description("Documentação da API"));
    }
}
