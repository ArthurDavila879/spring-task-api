package projeto.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.spring.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
