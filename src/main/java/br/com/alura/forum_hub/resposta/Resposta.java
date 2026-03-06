package br.com.alura.forum_hub.resposta;

import br.com.alura.forum_hub.domain.topico.Topico;
import br.com.alura.forum_hub.domain.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensagem;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    private Boolean solucao;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

}
