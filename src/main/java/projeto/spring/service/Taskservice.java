package projeto.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.spring.dto.TaskDTO;
import projeto.spring.model.Status;
import projeto.spring.model.Task;
import projeto.spring.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class Taskservice {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> listar() {
        return taskRepository.findAll();
    }

    public Task salvar(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setCreatedAt((LocalDateTime.now()));
        return taskRepository.save(task);

    }

    public Task deletar(String id) {
        if (id != null) {
            taskRepository.deleteById(id);
        }
        return null;

    }

    public Optional<Task> buscarPorId(String id) {
        return taskRepository.findById(id);
    }

    public Task atualizar(String id, TaskDTO taskDTO) {
        Task task1 = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if (taskDTO.getStatus() != null) {
            task1.setStatus(taskDTO.getStatus());
        }

        if (taskDTO.getTitle() != null) {
            task1.setTitle(taskDTO.getTitle());
        }

        if (taskDTO.getDescription() != null) {
            task1.setDescription(taskDTO.getDescription());
        }
        return taskRepository.save(task1);

    }
}
