package projeto.spring.dto;


import projeto.spring.model.task.Status;

public record TaskRequestDto (
     String title,
     String description,
     Status status,
     String idUser
){}