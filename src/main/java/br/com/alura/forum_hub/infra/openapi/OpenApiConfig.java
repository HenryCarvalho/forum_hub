package br.com.alura.forum_hub.infra.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "ForumHub API",
                version = "1.0",
                description = "API REST para gerenciamento de tópicos do FórumHub com autenticação JWT e controle de acesso por perfil.",
                contact = @Contact(
                        name = "Henrique Carvalho"
                )
        ),
        security = @SecurityRequirement(name = "bearer-key")
)
@SecurityScheme(
        name = "bearer-key",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
}