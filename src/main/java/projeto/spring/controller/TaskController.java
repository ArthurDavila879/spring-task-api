package projeto.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
}