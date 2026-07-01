package projeto.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projeto.spring.dto.TaskRequestDto;
import projeto.spring.dto.TaskResponseDto;
import projeto.spring.model.task.Status;
import projeto.spring.model.task.Task;
import projeto.spring.model.user.User;
import projeto.spring.model.user.UserRole;
import projeto.spring.repository.TaskRepository;
import projeto.spring.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskserviceTest {

    @InjectMocks
    private Taskservice taskservice;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Deve listar tarefas com sucesso")
    void listarTasksComSucesso() {
        User user = createUser("1");
        Task task = new Task(1L, "Teste Unitario", "Estudando testes unitarios", Status.IN_PROGRESS, LocalDateTime.now(), user);
        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<TaskResponseDto> tarefas = taskservice.listar();

        assertThat(tarefas).hasSize(1);
        assertThat(tarefas.getFirst().id()).isEqualTo(1L);
        assertThat(tarefas.getFirst().title()).isEqualTo("Teste Unitario");
    }

    @Test
    @DisplayName("Deve salvar tarefa com sucesso")
    void salvarTaskComSucesso() {
        User user = createUser("1");
        TaskRequestDto request = new TaskRequestDto("Teste Unitario", "Estudando testes unitarios", Status.IN_PROGRESS, "1");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        TaskResponseDto response = taskservice.salvar(request);

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(taskCaptor.capture());
        Task savedTask = taskCaptor.getValue();

        assertThat(response.title()).isEqualTo("Teste Unitario");
        assertThat(savedTask.getTitle()).isEqualTo("Teste Unitario");
        assertThat(savedTask.getDescription()).isEqualTo("Estudando testes unitarios");
        assertThat(savedTask.getStatus()).isEqualTo(Status.IN_PROGRESS);
        assertThat(savedTask.getCreatedAt()).isNotNull();
        assertThat(savedTask.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("Deve lançar erro ao salvar tarefa para usuario inexistente")
    void salvarTaskUsuarioInexistente() {
        TaskRequestDto request = new TaskRequestDto("Teste Unitario", "Estudando testes unitarios", Status.IN_PROGRESS, "1");
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskservice.salvar(request));
        verify(taskRepository, never()).save(org.mockito.Mockito.any(Task.class));
    }

    @Test
    @DisplayName("Deve buscar tarefa por id com sucesso")
    void buscarPorIdComSucesso() {
        Task task = new Task(1L, "Teste Unitario", "Descricao", Status.TODO, LocalDateTime.now(), createUser("1"));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponseDto response = taskservice.buscarPorId(1L);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.title()).isEqualTo("Teste Unitario");
    }

    @Test
    @DisplayName("Deve lançar erro ao buscar tarefa inexistente")
    void buscarPorIdInexistente() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskservice.buscarPorId(1L));
    }

    @Test
    @DisplayName("Deve deletar tarefa quando id for informado")
    void deletarComId() {
        taskservice.deletar(1L);

        verify(taskRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Nao deve deletar tarefa quando id for nulo")
    void deletarComIdNulo() {
        taskservice.deletar(null);

        verify(taskRepository, never()).deleteById(org.mockito.Mockito.anyLong());
    }

    @Test
    @DisplayName("Deve atualizar todos os campos da tarefa")
    void atualizarTodosOsCampos() {
        Task task = new Task(1L, "Antigo", "Descricao antiga", Status.TODO, LocalDateTime.now(), createUser("1"));
        TaskRequestDto request = new TaskRequestDto("Novo", "Descricao nova", Status.DONE, null);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponseDto response = taskservice.atualizar(1L, request);

        assertThat(response.title()).isEqualTo("Novo");
        assertThat(task.getTitle()).isEqualTo("Novo");
        assertThat(task.getDescription()).isEqualTo("Descricao nova");
        assertThat(task.getStatus()).isEqualTo(Status.DONE);
    }

    @Test
    @DisplayName("Deve ignorar campos nulos ao atualizar tarefa")
    void atualizarIgnoraCamposNulos() {
        Task task = new Task(1L, "Antigo", "Descricao antiga", Status.TODO, LocalDateTime.now(), createUser("1"));
        TaskRequestDto request = new TaskRequestDto(null, null, null, null);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponseDto response = taskservice.atualizar(1L, request);

        assertThat(response.title()).isEqualTo("Antigo");
        assertThat(task.getTitle()).isEqualTo("Antigo");
        assertThat(task.getDescription()).isEqualTo("Descricao antiga");
        assertThat(task.getStatus()).isEqualTo(Status.TODO);
    }

    @Test
    @DisplayName("Deve lançar erro ao atualizar tarefa inexistente")
    void atualizarTaskInexistente() {
        TaskRequestDto request = new TaskRequestDto("Novo", "Descricao nova", Status.DONE, null);
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskservice.atualizar(1L, request));
    }

    @Test
    @DisplayName("Deve listar tarefas por usuario")
    void getByUserComSucesso() {
        User user = createUser("1");
        user.setListTasks(List.of(
                new Task(1L, "Task 1", "Descricao 1", Status.TODO, LocalDateTime.now(), user),
                new Task(2L, "Task 2", "Descricao 2", Status.DONE, LocalDateTime.now(), user)
        ));
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        List<TaskResponseDto> response = taskservice.getByUser("1");

        assertThat(response).hasSize(2);
        assertThat(response).extracting(TaskResponseDto::title).containsExactly("Task 1", "Task 2");
    }

    @Test
    @DisplayName("Deve lançar erro ao listar tarefas de usuario inexistente")
    void getByUserInexistente() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskservice.getByUser("1"));
    }

    private User createUser(String id) {
        return new User(id, "teste", "1234", UserRole.ADMIN, new ArrayList<>());
    }
}
