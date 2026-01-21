package com.detyparfum.gestao.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API Loja Dety Parfum",
        version = "1.0.0",
        description = "Documentação completa da API de gestão da loja Dety Parfum.",
        contact = @Contact(
            name = "Equipe Dety Parfum",
            email = "suporte@detyparfum.com"
        ),
        license = @License(
            name = "Proprietária",
            url = "https://detyparfum.com/termos"
        )
    ),
    servers = {
        @Server(url = "/", description = "Servidor padrão")
    },
    security = {
        @SecurityRequirement(name = "sessionAuth")
    }
)
@SecurityScheme(
    name = "sessionAuth",
    type = SecuritySchemeType.APIKEY,
    in = SecuritySchemeIn.COOKIE,
    paramName = "JSESSIONID"
)
public class OpenAPIConfig {
}
