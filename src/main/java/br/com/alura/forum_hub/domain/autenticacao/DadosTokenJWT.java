package br.com.alura.forum_hub.domain.autenticacao;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DadosTokenJWT", description = "Token JWT retornado após autenticação")
public record DadosTokenJWT(

        @Schema(description = "Token JWT para autenticação Bearer", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token

) {
}