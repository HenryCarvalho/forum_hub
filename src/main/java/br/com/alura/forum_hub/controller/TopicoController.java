package br.com.alura.forum_hub.controller;

import br.com.alura.forum_hub.domain.curso.Curso;
import br.com.alura.forum_hub.domain.curso.CursoRepository;
import br.com.alura.forum_hub.domain.topico.*;
import br.com.alura.forum_hub.domain.usuario.Usuario;
import br.com.alura.forum_hub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
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

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {

        var optionalTopico = topicoRepository.findById(id);

        if(optionalTopico.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var topico = optionalTopico.get();

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoTopico dados) {

        var optionalTopico = topicoRepository.findById(id);

        if(optionalTopico.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var topico = optionalTopico.get();

        if(topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())){
            throw new RuntimeException("Tópico duplicado");
        }

        topico.setTitulo(dados.titulo());
        topico.setMensagem(dados.mensagem());

        topicoRepository.save(topico);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PostMapping
    public ResponseEntity cadastrar(
            @RequestBody @Valid DadosCadastroTopico dados,
            UriComponentsBuilder uriBuilder) {

        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            throw new RuntimeException("Tópico duplicado");
        }

        Usuario autor = usuarioRepository.findById(dados.autorId())
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

        return ResponseEntity.created(uri)
                .body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public Page<DadosListagemTopico> listar(
            @PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao) {
        return topicoRepository.findAll(paginacao).map(DadosListagemTopico::new);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable Long id){

        if(!topicoRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        topicoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
