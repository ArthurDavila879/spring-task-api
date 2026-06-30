package projeto.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import projeto.spring.dto.TaskRequestDto;
import projeto.spring.dto.TaskResponseDto;
import projeto.spring.dto.UserRequestDto;
import projeto.spring.model.task.Status;
import projeto.spring.model.task.Task;
import projeto.spring.model.user.User;
import projeto.spring.model.user.UserRole;
import projeto.spring.repository.TaskRepository;
import projeto.spring.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class TaskserviceTest {

    @InjectMocks
    private Taskservice taskservice;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

@Test
    @DisplayName("Deve buscar tarefas com sucesso")
    public void buscarTasks1(){
    User user = new User("1","Teste","1234",UserRole.ADMIN, new ArrayList<>());
    Task task = new Task(1L,"Teste Unitario","Estudando testes unitario", Status.IN_PROGRESS,LocalDateTime.now(),user);
    Mockito.when(taskRepository.findAll()).thenReturn(Collections.singletonList(task));
   List<TaskResponseDto> tarefas =  taskservice.listar();
    System.out.println(tarefas);
    Assertions.assertEquals(1, tarefas.size());
}
@Test
    @DisplayName("Deve salvar tarefas com sucesso")
    public void salvarTask1(){
        User user = new User("1","Teste","1234",UserRole.ADMIN, new ArrayList<>());
        TaskRequestDto taskRequestDto = new TaskRequestDto("Teste Unitario","Estudando testes unitario", Status.IN_PROGRESS,"1");
        Mockito.when(userRepository.findById("1")).thenReturn(java.util.Optional.of(user));
        TaskResponseDto taskResponseDto = taskservice.salvar(taskRequestDto);
        Assertions.assertEquals("Teste Unitario", taskResponseDto.title());
    }


}