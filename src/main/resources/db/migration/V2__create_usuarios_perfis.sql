CREATE TABLE usuarios (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          nome VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          senha VARCHAR(255) NOT NULL,
                          PRIMARY KEY (id),
                          UNIQUE KEY uk_usuarios_email (email)
);

CREATE TABLE perfis (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        nome VARCHAR(50) NOT NULL,
                        PRIMARY KEY (id),
                        UNIQUE KEY uk_perfis_nome (nome)
);

CREATE TABLE usuarios_perfis (
                                 usuario_id BIGINT NOT NULL,
                                 perfil_id BIGINT NOT NULL,
                                 PRIMARY KEY (usuario_id, perfil_id),
                                 CONSTRAINT fk_usuarios_perfis_usuario
                                     FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
                                 CONSTRAINT fk_usuarios_perfis_perfil
                                     FOREIGN KEY (perfil_id) REFERENCES perfis(id)
);