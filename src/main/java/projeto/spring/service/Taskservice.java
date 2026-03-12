package projeto.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
      public Task salvar(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);

    }
     public Task deletar(String id) {
        if (id != null) {
            taskRepository.deleteById(id);
        }
        return null;

    }
    public Optional<Task> buscarPorId(String id){
        return taskRepository.findById(id);
    }
}
