package projeto.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projeto.spring.dto.UserRequestDto;
import projeto.spring.dto.UserResponseDto;
import projeto.spring.model.user.User;
import projeto.spring.model.user.UserRole;
import projeto.spring.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Deve listar usuarios com sucesso")
    void getUsersComSucesso() {
        User admin = createUser("1", "admin");
        User user = createUser("2", "user");
        when(userRepository.findAll()).thenReturn(List.of(admin, user));

        List<UserResponseDto> response = userService.getUsers();

        assertThat(response).hasSize(2);
        assertThat(response).extracting(UserResponseDto::login).containsExactly("admin", "user");
    }
    private User createUser(String id, String login) {
        return new User(id, login, "1234", UserRole.USER, new ArrayList<>());
    }
}
