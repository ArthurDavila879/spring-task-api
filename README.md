# Spring Task API

Uma API REST construída com **Java 21 + Spring Boot** para gerenciamento de usuários, autenticação e tarefas.

Este projeto demonstra:
- Arquitetura backend em camadas (Controller → Service → Repository → Database)
- Autenticação baseada em JWT com Spring Security
- Autorização baseada em papéis (roles)
- Persistência em MySQL com Spring Data JPA

## Tecnologias

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Spring Security
- JWT (`java-jwt`)
- MySQL
- Maven
- Lombok
- Springdoc OpenAPI (Swagger UI)
- JUnit 5 + Mockito (testes unitários)
- Docker & Docker Compose

## Estrutura do Projeto

```text
src/main/java/projeto/spring
├── controller
│   ├── AuthenticationController.java
│   ├── TaskController.java
│   └── UserController.java
├── dto
├── model
│   ├── task
│   └── user
├── repository
├── security
└── service
```

## Funcionalidades Principais

- Registro e login de usuários
- Geração e validação de token JWT
- Endpoints protegidos usando `Authorization: Bearer <token>`
- Operações CRUD de tarefas
- Busca de tarefas por usuário
- Listagem de usuários
- Documentação interativa da API via Swagger UI
- Cobertura de testes unitários (services e repositories) com JUnit 5 e Mockito

## Segurança e Papéis

A API é stateless e protegida por um filtro JWT.

### Endpoints públicos
- `POST /auth/login`
- `POST /auth/register`

### Endpoints restritos
- `POST /tasks` exige papel `ADMIN`
- Todos os demais endpoints exigem autenticação

Papéis disponíveis:
- `ADMIN`
- `USER`

## Endpoints da API

### Autenticação
- `POST /auth/register`
- `POST /auth/login`

#### Exemplo de registro
```json
{
  "login": "admin",
  "password": "123456",
  "role": "ADMIN"
}
```

#### Exemplo de login
```json
{
  "login": "admin",
  "password": "123456"
}
```

### Usuários
- `GET /users`

> A criação de usuários é feita exclusivamente via `POST /auth/register`, garantindo que a senha sempre passe pelo hash BCrypt antes de ser persistida.

### Tarefas
- `GET /tasks`
- `POST /tasks`
- `GET /tasks/{id}`
- `PUT /tasks/{id}`
- `DELETE /tasks/{id}`
- `GET /tasks/user/{id}`

#### Exemplo de criação de tarefa
```json
{
  "title": "Estudar Spring Security",
  "description": "Implementar autenticação JWT",
  "status": "TODO",
  "idUser": "<uuid-do-usuario>"
}
```

Valores possíveis para status:
- `TODO`
- `IN_PROGRESS`
- `DONE`

## Configuração Local

### 1) Clone o repositório
```bash
git clone <url-do-seu-repositorio>
cd spring-task-api
```

### 2) Configure banco de dados e segredo
Atualize `src/main/resources/application.properties` conforme necessário:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/***
spring.datasource.username=***
spring.datasource.password=***
api.security.token.secret=<SEU_SEGREDO>
```

> Observação: no arquivo atual, o segredo é lido de `${JWT_SECRET}`.

### 3) Execute a aplicação
Usando o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

A API ficará disponível em:
- `http://localhost:8080`

### Documentação interativa (Swagger)

Com a aplicação rodando, a documentação da API pode ser acessada em:
- `http://localhost:8080/swagger-ui/index.html`

## Build e Testes

```bash
./mvnw clean test
./mvnw clean package
```

O projeto conta com testes unitários para a camada de serviço (`Taskservice`, `UserService`) e testes de integração para os repositórios (`TaskRepository`, `UserRepository`), utilizando **JUnit 5** e **Mockito**.

## Rodando com Docker

Como alternativa à configuração local, o projeto pode ser executado inteiramente via Docker, sem precisar instalar Java, Maven ou MySQL na máquina.

### Pré-requisitos
- Docker
- Docker Compose

### 1) Crie o arquivo `.env`

Na raiz do projeto, crie um arquivo `.env` com as variáveis sensíveis:

```env
JWT_SECRET=seu_segredo_aqui
DB_PASSWORD=sua_senha_aqui
```

### 2) Suba os containers

```bash
docker compose up --build
```

Esse comando builda a imagem da aplicação (via multi-stage build: JDK 21 para compilar, JRE para rodar) e sobe dois containers: a API e o banco MySQL, já conectados entre si.

A API ficará disponível em `http://localhost:8080`, e o Swagger em `http://localhost:8080/swagger-ui/index.html`, como na execução local.

### Comandos úteis

| Comando | Descrição |
|---|---|
| `docker compose up --build` | Builda e sobe a aplicação e o banco |
| `docker compose down` | Para os containers (mantém os dados) |
| `docker compose down -v` | Para os containers e apaga os dados do banco |
| `docker compose logs -f app` | Acompanha os logs da aplicação em tempo real |

> Os dados do MySQL são persistidos em um volume Docker (`mysql_data`), sobrevivendo a reinicializações dos containers.

## Observações

- O esquema do banco é gerenciado via `spring.jpa.hibernate.ddl-auto=update`.
- Atualmente, os endpoints de tarefas retornam uma resposta compacta (`id` e `title`).
