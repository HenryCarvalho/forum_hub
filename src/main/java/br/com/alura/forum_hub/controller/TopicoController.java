package br.com.alura.forum_hub.controller;

import br.com.alura.forum_hub.domain.curso.Curso;
import br.com.alura.forum_hub.domain.curso.CursoRepository;
import br.com.alura.forum_hub.domain.topico.DadosAtualizacaoTopico;
import br.com.alura.forum_hub.domain.topico.DadosCadastroTopico;
import br.com.alura.forum_hub.domain.topico.DadosDetalhamentoTopico;
import br.com.alura.forum_hub.domain.topico.DadosListagemTopico;
import br.com.alura.forum_hub.domain.topico.Topico;
import br.com.alura.forum_hub.domain.topico.TopicoRepository;
import br.com.alura.forum_hub.domain.topico.TopicoStatus;
import br.com.alura.forum_hub.domain.usuario.Usuario;
import br.com.alura.forum_hub.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springdoc.core.annotations.ParameterObject;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
@Tag(name = "Tópicos", description = "Operações de gerenciamento de tópicos")
public class TopicoController {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    public TopicoController(
            TopicoRepository topicoRepository,
            UsuarioRepository usuarioRepository,
            CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

    @Operation(
            summary = "Cadastrar tópico",
            description = "Cadastra um novo tópico no sistema."
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<DadosDetalhamentoTopico> cadastrar(
            @RequestBody @Valid DadosCadastroTopico dados,
            UriComponentsBuilder uriBuilder) {

        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            throw new RuntimeException("Tópico duplicado");
        }

        Usuario autor = (Usuario) usuarioRepository.findById(dados.autorId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        Curso curso = cursoRepository.findById(dados.cursoId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        Topico topico = new Topico(
                null,
                dados.titulo(),
                dados.mensagem(),
                LocalDateTime.now(),
                TopicoStatus.NAO_RESPONDIDO,
                autor,
                curso
        );

        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}")
                .buildAndExpand(topico.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    @Operation(
            summary = "Listar tópicos",
            description = "Lista os tópicos com paginação e ordenação."
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public Page<DadosListagemTopico> listar(
            @ParameterObject
            @PageableDefault(size = 10, sort = {"dataCriacao"})
            Pageable paginacao) {
        return topicoRepository.findAll(paginacao).map(DadosListagemTopico::new);
    }

    @Operation(
            summary = "Detalhar tópico",
            description = "Retorna os dados detalhados de um tópico pelo ID."
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(
            @PathVariable
            @Parameter(description = "ID do tópico")
            Long id) {

        var optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DadosDetalhamentoTopico(optionalTopico.get()));
    }

    @Operation(
            summary = "Atualizar tópico",
            description = "Atualiza título e mensagem de um tópico existente."
    )
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(
            @PathVariable
            @Parameter(description = "ID do tópico")
            Long id,
            @RequestBody @Valid DadosAtualizacaoTopico dados) {

        var optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var topico = optionalTopico.get();

        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            throw new RuntimeException("Tópico duplicado");
        }

        topico.setTitulo(dados.titulo());
        topico.setMensagem(dados.mensagem());

        topicoRepository.save(topico);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @Operation(
            summary = "Excluir tópico",
            description = "Exclui um tópico pelo ID. Requer perfil ADMIN."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable
            @Parameter(description = "ID do tópico")
            Long id) {

        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        topicoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}