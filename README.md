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
- Endpoints básicos de listagem e atualização de usuários

## Segurança e Papéis

A API é stateless e protegida por um filtro JWT.

### Endpoints públicos
- `POST /auth/login`
- `POST /auth/register`

### Endpoints restritos
- `POST /tasks` exige papel `ADMIN`
- `POST /users` exige papel `ADMIN`
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
- `POST /users`
- `PUT /users/{id}`

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

> Observação: no arquivo atual, o segredo é lido de `${CHAVE-API}`.

### 3) Execute a aplicação
Usando o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

A API ficará disponível em:
- `http://localhost:8080`

## Build e Testes

```bash
./mvnw clean test
./mvnw clean package
```

## Observações

- O esquema do banco é gerenciado via `spring.jpa.hibernate.ddl-auto=update`.
- Atualmente, os endpoints de tarefas retornam uma resposta compacta (`id` e `title`).
