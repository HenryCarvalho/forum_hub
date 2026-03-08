package br.com.alura.forum_hub.domain.topico;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "DadosAtualizacaoTopico", description = "Dados para atualização de um tópico")
public record DadosAtualizacaoTopico(

        @Schema(description = "Novo título do tópico", example = "Erro no Spring Boot atualizado")
        @NotBlank
        String titulo,

        @Schema(description = "Nova mensagem do tópico", example = "Agora a aplicação inicia após corrigir o Flyway")
        @NotBlank
        String mensagem

) {
}