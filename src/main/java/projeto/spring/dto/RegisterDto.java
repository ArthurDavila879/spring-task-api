package projeto.spring.dto;

import projeto.spring.model.user.UserRole;

public record RegisterDto (String login, String password, UserRole role){
}
