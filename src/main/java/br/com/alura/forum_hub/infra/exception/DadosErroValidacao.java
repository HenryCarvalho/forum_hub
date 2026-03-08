package br.com.alura.forum_hub.infra.exception;

public record DadosErroValidacao(
        String campo,
        String mensagem
) {
}
