package br.com.alura.forum_hub.domain.topico;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "DadosCadastroTopico", description = "Dados para cadastro de um novo tópico")
public record DadosCadastroTopico(

        @Schema(description = "Título do tópico", example = "Erro ao subir Spring Boot")
        @NotBlank
        String titulo,

        @Schema(description = "Mensagem do tópico", example = "Minha aplicação não inicia após configurar o Flyway")
        @NotBlank
        String mensagem,

        @Schema(description = "ID do autor do tópico", example = "1")
        @NotNull
        Long autorId,

        @Schema(description = "ID do curso relacionado ao tópico", example = "1")
        @NotNull
        Long cursoId

) {
}