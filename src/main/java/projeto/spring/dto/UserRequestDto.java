package projeto.spring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDto (
     String login,
    @NotBlank
     String password
)
{}