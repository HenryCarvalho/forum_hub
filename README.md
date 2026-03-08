# ForumHub API

API REST desenvolvida com **Spring Boot** para gerenciamento de tópicos
de um fórum.

O sistema permite que usuários autenticados criem, listem, atualizem e
excluam tópicos, utilizando **autenticação JWT** e controle de acesso
por **roles (USER e ADMIN)**.

------------------------------------------------------------------------

# Tecnologias utilizadas

-   Java 17
-   Spring Boot
-   Spring Security
-   JWT (JSON Web Token)
-   Spring Data JPA
-   Hibernate
-   Flyway
-   MySQL
-   Maven
-   Swagger / OpenAPI
-   Lombok

------------------------------------------------------------------------

# Arquitetura do Projeto

O projeto segue uma arquitetura em camadas:

controller → domain → repository → infra

### Camadas

**Controller**\
Responsável pelos endpoints da API.

**Domain**\
Contém entidades, DTOs e regras de negócio.

**Repository**\
Comunicação com o banco de dados.

**Infra**\
Configurações da aplicação, segurança e tratamento de erros.

------------------------------------------------------------------------

# Autenticação

A autenticação é feita utilizando **JWT (JSON Web Token)**.

Fluxo:

1.  Usuário realiza login
2.  API gera um token JWT
3.  O token deve ser enviado nas requisições protegidas

Header:

Authorization: Bearer TOKEN

------------------------------------------------------------------------

# Controle de acesso (Roles)

O sistema possui dois perfis:

### USER

Pode:

-   Criar tópicos
-   Listar tópicos
-   Atualizar tópicos
-   Visualizar tópicos

### ADMIN

Pode:

-   Todas as ações do USER
-   Excluir tópicos

------------------------------------------------------------------------

# Endpoints da API

## Autenticação

### Login

POST /login

Request:

{ "email": "henrique@email.com", "senha": "123456" }

Response:

{ "token": "jwt_token" }

------------------------------------------------------------------------

## Tópicos

### Criar tópico

POST /topicos

Request:

{ "titulo": "Erro ao subir Spring Boot", "mensagem": "Minha aplicação
não inicia", "autorId": 1, "cursoId": 1 }

------------------------------------------------------------------------

### Listar tópicos

GET /topicos

Parâmetros:

page\
size\
sort

Exemplo:

/topicos?page=0&size=10&sort=dataCriacao,desc

------------------------------------------------------------------------

### Detalhar tópico

GET /topicos/{id}

------------------------------------------------------------------------

### Atualizar tópico

PUT /topicos/{id}

Request:

{ "titulo": "Novo título", "mensagem": "Nova mensagem" }

------------------------------------------------------------------------

### Excluir tópico (ADMIN)

DELETE /topicos/{id}

------------------------------------------------------------------------

# Documentação da API

A documentação está disponível via Swagger.

URL:

http://localhost:8080/swagger-ui/index.html

Através do Swagger é possível:

-   visualizar todos os endpoints
-   testar a API
-   autenticar utilizando JWT

------------------------------------------------------------------------

# Tratamento de erros

A API possui tratamento global de exceções utilizando:

@RestControllerAdvice

Exemplos de erros tratados:

-   validação de campos
-   login inválido
-   acesso negado
-   recurso não encontrado
-   dados duplicados

------------------------------------------------------------------------

# Como executar o projeto

### 1. Clonar o repositório

git clone https://github.com/HenryCarvalho/forum_hub.git

------------------------------------------------------------------------

### 2. Configurar banco de dados

Criar banco:

forumhub

Configurar no:

application.properties

------------------------------------------------------------------------

### 3. Rodar a aplicação

Via Maven:

mvn spring-boot:run

Ou executar a classe:

ForumHubApplication

------------------------------------------------------------------------

# Usuários de teste

### ADMIN

email: henrique@email.com\
senha: 123456

### USER

email: dayane@email.com\
senha: 654321

------------------------------------------------------------------------

# Melhorias futuras

-   respostas padronizadas de erro
-   paginação customizada
-   criação de respostas para tópicos
-   sistema de categorias
-   deploy em cloud
-   testes automatizados

------------------------------------------------------------------------

# Autor

Projeto desenvolvido por **Henrique Carvalho**.
