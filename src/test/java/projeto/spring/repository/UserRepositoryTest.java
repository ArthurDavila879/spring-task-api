package projeto.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import projeto.spring.model.user.User;
import projeto.spring.model.user.UserRole;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Deve encontrar usuario pelo login")
    void findByLoginSuccess() {
        User user = new User("arthur", "1234", UserRole.USER);
        userRepository.save(user);

        UserDetails userDetails = userRepository.findByLogin("arthur");

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("arthur");
    }

    @Test
    @DisplayName("Deve retornar nulo quando login nao existe")
    void findByLoginNotFound() {
        UserDetails userDetails = userRepository.findByLogin("arthur");

        assertThat(userDetails).isNull();
    }
}
