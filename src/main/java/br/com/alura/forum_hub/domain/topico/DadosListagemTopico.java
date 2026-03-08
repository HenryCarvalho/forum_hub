package br.com.alura.forum_hub.domain.topico;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "DadosListagemTopico", description = "Dados resumidos para listagem de tópicos")
public record DadosListagemTopico(

        @Schema(description = "ID do tópico", example = "1")
        Long id,

        @Schema(description = "Título do tópico", example = "Erro ao subir Spring Boot")
        String titulo,

        @Schema(description = "Mensagem do tópico", example = "Minha aplicação não inicia após configurar o Flyway")
        String mensagem,

        @Schema(description = "Data e hora de criação do tópico", example = "2026-03-08T17:36:53")
        LocalDateTime dataCriacao,

        @Schema(description = "Status do tópico", example = "NAO_RESPONDIDO")
        TopicoStatus status

) {
    public DadosListagemTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus()
        );
    }
}
