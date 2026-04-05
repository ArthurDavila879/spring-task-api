package projeto.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.spring.dto.TaskRequestDto;
import projeto.spring.dto.TaskResponseDto;
import projeto.spring.dto.UserResponseDto;
import projeto.spring.model.Task;
import projeto.spring.model.User;
import projeto.spring.repository.TaskRepository;
import projeto.spring.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class Taskservice {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<TaskResponseDto> listar() {
       return taskRepository.findAll().stream()
                .map(task -> new TaskResponseDto(task.getId(), task.getTitle()))
                .toList();

    }

    public TaskResponseDto salvar(TaskRequestDto taskRequestDto) {
        Task task = new Task();
        task.setTitle(taskRequestDto.title());
        task.setDescription(taskRequestDto.description());
        task.setStatus(taskRequestDto.status());
        task.setCreatedAt((LocalDateTime.now()));

        User user = userRepository.findById(taskRequestDto.idUser()).orElseThrow( () -> new RuntimeException("Erro ao encontrar usuario"));
        task.setUser(user);

        user.inserirTask(task);
        taskRepository.save(task);
        return (new TaskResponseDto(task.getId(),task.getTitle()));

    }

    public Task deletar(Long id) {
        if (id != null) {
            taskRepository.deleteById(id);
        }
        return null;

    }

    public TaskResponseDto buscarPorId(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(()->new RuntimeException("Erro ao encontrar usuario"));
        return (new TaskResponseDto(task.getId(),task.getTitle()));
    }

    public TaskResponseDto atualizar(Long id, TaskRequestDto taskRequestDto) {
        Task task1 = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if (taskRequestDto.status() != null) {
            task1.setStatus(taskRequestDto.status());
        }

        if (taskRequestDto.title() != null) {
            task1.setTitle(taskRequestDto.title());
        }

        if (taskRequestDto.description() != null) {
            task1.setDescription(taskRequestDto.description());
        }

        return (new TaskResponseDto(task1.getId(),task1.getTitle()));

    }
    public List<TaskResponseDto> getByUser(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("Erro ao encontrar usuario"));
        return user.getListTasks().stream().map(task -> new TaskResponseDto(task.getId(),task.getTitle())).toList();

    }
}
