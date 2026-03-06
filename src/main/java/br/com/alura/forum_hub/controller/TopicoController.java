package br.com.alura.forum_hub.controller;

import br.com.alura.forum_hub.domain.curso.Curso;
import br.com.alura.forum_hub.domain.curso.CursoRepository;
import br.com.alura.forum_hub.domain.topico.DadosCadastroTopico;
import br.com.alura.forum_hub.domain.topico.Topico;
import br.com.alura.forum_hub.domain.topico.TopicoRepository;
import br.com.alura.forum_hub.domain.topico.TopicoStatus;
import br.com.alura.forum_hub.domain.usuario.Usuario;
import br.com.alura.forum_hub.domain.usuario.UsuarioRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroTopico dados) {

        if(topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())){
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

    }
}
