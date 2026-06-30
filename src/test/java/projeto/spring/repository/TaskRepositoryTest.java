package projeto.spring.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import projeto.spring.dto.TaskRequestDto;
import projeto.spring.dto.TaskResponseDto;
import projeto.spring.model.task.Status;
import projeto.spring.model.task.Task;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static projeto.spring.model.task.Status.TODO;

@DataJpaTest
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    TaskRepository taskRepository;

    @Test
    @DisplayName(" Task no banco de dados")
    void findTaskByTitleSuccess() {
       TaskRequestDto task = new TaskRequestDto("Teste Unitario","Estudando testes unitario", Status.IN_PROGRESS,"1");
       this.createTask(task);
        Optional<Task> task1 = this.taskRepository.findTaskByTitle("Teste Unitario");
        assertThat(task1.isPresent()).isTrue();

    }
    @Test
    @DisplayName(" Task nao esta no banco de dados")
    void findTaskByTitleErro() {
        Optional<Task> task1 = this.taskRepository.findTaskByTitle("Teste Unitario");
        assertThat(task1.isPresent()).isTrue();

    }

    private Task createTask(TaskRequestDto data){
          Task task = new Task(data);
          this.entityManager.persist(task);
          return new Task();
    }
}