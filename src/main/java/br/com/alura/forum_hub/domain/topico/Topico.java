package br.com.alura.forum_hub.domain.topico;

import br.com.alura.forum_hub.domain.curso.Curso;
import br.com.alura.forum_hub.domain.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "topicos")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensagem;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private TopicoStatus status;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Topico() {
    }

    public Topico(Long id, String titulo, String mensagem, LocalDateTime dataCriacao, TopicoStatus status, Usuario autor, Curso curso) {
        this.id = id;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.autor = autor;
        this.curso = curso;
    }
}
