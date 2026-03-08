package br.com.alura.forum_hub.controller;

import br.com.alura.forum_hub.domain.autenticacao.DadosAutenticacao;
import br.com.alura.forum_hub.domain.autenticacao.DadosTokenJWT;
import br.com.alura.forum_hub.domain.usuario.Usuario;
import br.com.alura.forum_hub.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticação", description = "Operações de login e geração de token JWT")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuário com email e senha e retorna um token JWT.",
            security = {}
    )
    @PostMapping
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DadosAutenticacao dados) {

        var authenticationToken =
                new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

        var authentication = authenticationManager.authenticate(authenticationToken);

        var usuario = (Usuario) authentication.getPrincipal();

        var token = tokenService.gerarToken(usuario);

        return ResponseEntity.ok(new DadosTokenJWT(token));
    }
}