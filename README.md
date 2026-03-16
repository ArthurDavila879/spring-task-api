# Task Manager API

API REST desenvolvida em **Java com Spring Boot** para gerenciamento de tarefas.
O projeto implementa operações CRUD completas, permitindo criar, listar, atualizar e remover tarefas.

Este projeto foi desenvolvido com o objetivo de praticar conceitos de **desenvolvimento backend, arquitetura em camadas e APIs REST**.

---
<img width="1073" height="342" alt="image" src="https://github.com/user-attachments/assets/d7223838-446f-40b4-9772-6ba52a7a0d41" />

---

## Tecnologias utilizadas

* Java
* Spring Boot
* Spring Data JPA
* MySQL
* Lombok
* Maven

---

## Arquitetura do Projeto

O projeto segue uma arquitetura em camadas:

```
Controller → Service → Repository → Database
```

Estrutura de pastas:

```
src/main/java
│
├── controller
│   └── TaskController
│
├── service
│   └── TaskService
│
├── repository
│   └── TaskRepository
│
├── model
│   └── Task
│
└── dto
    └── TaskDTO
```

---

## Funcionalidades da API

* Criar tarefa
* Listar todas as tarefas
* Buscar tarefa por ID
* Atualizar tarefa
* Deletar tarefa

---

## Endpoints

### Criar tarefa

POST /tasks

Exemplo de requisição:

```json
{
  "title": "Estudar Spring Boot",
  "description": "Criar uma API REST",
  "status": "TODO"
}
```

---

### Listar tarefas

GET /tasks

Resposta:

```json
[
  {
    "id": "uuid",
    "title": "Estudar Spring Boot",
    "description": "Criar uma API REST",
    "status": "TODO"
  }
]
```

---

### Buscar tarefa por ID

GET /tasks/{id}

---

### Atualizar tarefa

PUT /tasks/{id}

Exemplo:

```json
{
  "status": "DONE"
}
```

---

### Deletar tarefa

DELETE /tasks/{id}

---

## Testando a API

As requisições podem ser testadas utilizando o **Postman** ou qualquer cliente HTTP.

Base URL:

```
http://localhost:8080/tasks
```

---

## Objetivo do projeto

Este projeto foi desenvolvido para:

* Praticar desenvolvimento de APIs REST
* Aplicar conceitos de arquitetura em camadas
* Trabalhar com persistência de dados utilizando JPA
* Utilizar controle de versão com Git e GitHub
