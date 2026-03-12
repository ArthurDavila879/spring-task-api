package projeto.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projeto.spring.dto.TaskDTO;
import projeto.spring.model.Status;
import projeto.spring.model.Task;
import projeto.spring.service.Taskservice;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController()
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private Taskservice taskservice;

    @GetMapping
    public List<Task> listar() {
        return taskservice.listar();
    }

    @PostMapping
    public Task salvar(@RequestBody TaskDTO taskDTO) {
        return taskservice.salvar(taskDTO);
    }

    @DeleteMapping("/{id}")
    public Task deletar(@PathVariable String id) {
        taskservice.deletar(id);
        return null;
    }

    @GetMapping("/{id}")
    public Optional<Task> buscarPorId(@PathVariable String id) {
        return taskservice.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Task atualizar(@PathVariable String id, @RequestBody TaskDTO taskDTO) {
        return taskservice.atualizar(id, taskDTO);
    }
}