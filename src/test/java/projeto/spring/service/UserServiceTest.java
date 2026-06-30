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

    @Test
    @DisplayName("Deve salvar usuario com sucesso")
    void saveUserComSucesso() {
        UserRequestDto request = new UserRequestDto("arthur", "1234");

        UserResponseDto response = userService.saveUser(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertThat(response.login()).isEqualTo("arthur");
        assertThat(savedUser.getLogin()).isEqualTo("arthur");
        assertThat(savedUser.getPassword()).isEqualTo("1234");
    }

    @Test
    @DisplayName("Deve atualizar login e senha do usuario")
    void putUserAtualizaLoginESenha() {
        User user = createUser("1", "antigo");
        UserRequestDto request = new UserRequestDto("novo", "nova-senha");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        UserResponseDto response = userService.putUser("1", request);

        assertThat(response.login()).isEqualTo("novo");
        assertThat(user.getLogin()).isEqualTo("novo");
        assertThat(user.getPassword()).isEqualTo("nova-senha");
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Deve atualizar apenas login quando senha for nula")
    void putUserAtualizaApenasLogin() {
        User user = createUser("1", "antigo");
        UserRequestDto request = new UserRequestDto("novo", null);
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        UserResponseDto response = userService.putUser("1", request);

        assertThat(response.login()).isEqualTo("novo");
        assertThat(user.getLogin()).isEqualTo("novo");
        assertThat(user.getPassword()).isEqualTo("1234");
    }

    @Test
    @DisplayName("Deve atualizar apenas senha quando login for nulo")
    void putUserAtualizaApenasSenha() {
        User user = createUser("1", "antigo");
        UserRequestDto request = new UserRequestDto(null, "nova-senha");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        UserResponseDto response = userService.putUser("1", request);

        assertThat(response.login()).isEqualTo("antigo");
        assertThat(user.getLogin()).isEqualTo("antigo");
        assertThat(user.getPassword()).isEqualTo("nova-senha");
    }

    @Test
    @DisplayName("Deve lançar erro ao atualizar usuario inexistente")
    void putUserInexistente() {
        UserRequestDto request = new UserRequestDto("novo", "nova-senha");
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.putUser("1", request));
    }

    private User createUser(String id, String login) {
        return new User(id, login, "1234", UserRole.USER, new ArrayList<>());
    }
}
