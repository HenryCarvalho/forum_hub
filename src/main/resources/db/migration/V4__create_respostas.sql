CREATE TABLE respostas (
                           id BIGINT NOT NULL AUTO_INCREMENT,
                           mensagem TEXT NOT NULL,
                           data_criacao DATETIME NOT NULL,
                           solucao BOOLEAN NOT NULL,

                           autor_id BIGINT NOT NULL,
                           topico_id BIGINT NOT NULL,

                           PRIMARY KEY (id),

                           CONSTRAINT fk_respostas_autor
                               FOREIGN KEY (autor_id) REFERENCES usuarios(id),

                           CONSTRAINT fk_respostas_topico
                               FOREIGN KEY (topico_id) REFERENCES topicos(id)
);

CREATE INDEX idx_respostas_autor_id ON respostas(autor_id);
CREATE INDEX idx_respostas_topico_id ON respostas(topico_id);