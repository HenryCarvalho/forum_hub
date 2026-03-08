package br.com.alura.forum_hub.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors()
                .stream()
                .map(erro -> new DadosErroValidacao(erro.getField(), erro.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> tratarErroBadCredentials() {
        return ResponseEntity.status(401).body("Login ou senha inválidos");
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<String> tratarErroAcessoNegado() {
        return ResponseEntity.status(403).body("Acesso negado");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> tratarErroRuntime(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
