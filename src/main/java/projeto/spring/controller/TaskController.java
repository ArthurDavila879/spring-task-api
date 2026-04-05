package projeto.spring.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projeto.spring.dto.TaskRequestDto;
import projeto.spring.dto.TaskResponseDto;
import projeto.spring.model.Task;
import projeto.spring.service.Taskservice;

import java.util.List;
import java.util.Optional;


@RestController()
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private Taskservice taskservice;

    @GetMapping
    public List<TaskResponseDto> listar() {
        return taskservice.listar();
    }

    @PostMapping
    public TaskResponseDto salvar(@Valid @RequestBody TaskRequestDto taskRequestDto) {
        return taskservice.salvar(taskRequestDto);
    }

    @DeleteMapping("/{id}")
    public TaskResponseDto deletar(@PathVariable @Valid Long id) {
        taskservice.deletar(id);
        return null;
    }

    @GetMapping("/{id}")
    public TaskResponseDto buscarPorId(@PathVariable @Valid Long id) {
        return taskservice.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public TaskResponseDto atualizar(@PathVariable  Long id, @RequestBody @Valid TaskRequestDto taskRequestDto) {
        return taskservice.atualizar(id, taskRequestDto);
    }

    @GetMapping("/user/{id}")
    public List<TaskResponseDto> getByUser(@PathVariable Long id){
        return taskservice.getByUser(id);
    }
}