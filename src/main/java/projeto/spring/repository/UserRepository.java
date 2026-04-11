package projeto.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import projeto.spring.model.user.User;

public interface UserRepository extends JpaRepository<User,String> {
    UserDetails findByLogin(String login);

}
