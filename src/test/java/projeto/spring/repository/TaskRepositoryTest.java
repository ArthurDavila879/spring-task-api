package projeto.spring.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import projeto.spring.dto.TaskRequestDto;
import projeto.spring.model.task.Status;
import projeto.spring.model.task.Task;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    TaskRepository taskRepository;

    @Test
    @DisplayName("Deve encontrar task pelo titulo")
    void findTaskByTitleSuccess() {
       TaskRequestDto task = new TaskRequestDto("Teste Unitario","Estudando testes unitario", Status.IN_PROGRESS,"1");
       this.createTask(task);
        Optional<Task> task1 = this.taskRepository.findTaskByTitle("Teste Unitario");

        assertThat(task1).isPresent();
        assertThat(task1.get().getTitle()).isEqualTo("Teste Unitario");

    }

    @Test
    @DisplayName("Deve retornar vazio quando task nao esta no banco de dados")
    void findTaskByTitleErro() {
        Optional<Task> task1 = this.taskRepository.findTaskByTitle("Teste Unitario");

        assertThat(task1).isEmpty();

    }

    @Test
    @DisplayName("Deve persistir status, descricao e data de criacao")
    void saveTaskComCamposPreenchidos() {
        TaskRequestDto data = new TaskRequestDto("Teste Unitario", "Estudando testes unitario", Status.IN_PROGRESS, "1");

        Task task = this.createTask(data);

        assertThat(task.getId()).isNotNull();
        assertThat(task.getDescription()).isEqualTo("Estudando testes unitario");
        assertThat(task.getStatus()).isEqualTo(Status.IN_PROGRESS);
        assertThat(task.getCreatedAt()).isNotNull();
    }

    private Task createTask(TaskRequestDto data){
          Task task = new Task(data);
          this.entityManager.persist(task);
          this.entityManager.flush();
          return task;
    }
}
