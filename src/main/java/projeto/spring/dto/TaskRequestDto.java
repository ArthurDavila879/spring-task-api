package projeto.spring.dto;


import projeto.spring.model.Status;

public record TaskRequestDto (
     String title,
     String description,
     Status status,
     Long idUser
){}