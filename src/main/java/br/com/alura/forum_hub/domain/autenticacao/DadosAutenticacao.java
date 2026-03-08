package br.com.alura.forum_hub.domain.autenticacao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "DadosAutenticacao", description = "Dados para autenticação do usuário")
public record DadosAutenticacao(

        @Schema(description = "Email do usuário", example = "henrique@email.com")
        @NotBlank
        @Email
        String email,

        @Schema(description = "Senha do usuário", example = "123456")
        @NotBlank
        String senha

) {
}
