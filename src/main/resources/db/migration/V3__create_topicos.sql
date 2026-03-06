CREATE TABLE topicos (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         titulo VARCHAR(255) NOT NULL,
                         mensagem TEXT NOT NULL,
                         data_criacao DATETIME NOT NULL,
                         status VARCHAR(20) NOT NULL,

                         autor_id BIGINT NOT NULL,
                         curso_id BIGINT NOT NULL,

                         PRIMARY KEY (id),

                         CONSTRAINT fk_topicos_autor
                             FOREIGN KEY (autor_id) REFERENCES usuarios(id),

                         CONSTRAINT fk_topicos_curso
                             FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

CREATE INDEX idx_topicos_autor_id ON topicos(autor_id);
CREATE INDEX idx_topicos_curso_id ON topicos(curso_id);