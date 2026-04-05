package projeto.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projeto.spring.model.task.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
