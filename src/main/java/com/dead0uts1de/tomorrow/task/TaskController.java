package com.dead0uts1de.tomorrow.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(path = "{taskId}")
    public Task getTaskById(@PathVariable("taskId") Long id) {
        return this.taskService.getTaskById(id);
    }

    @GetMapping
    public List<Task> getTasks() {
        return this.taskService.getTasks();
    }

    @PostMapping
    public void createTask(@RequestBody Task task) {
        this.taskService.createTask(task);
    }

    @DeleteMapping(path = "{taskId}")
    public void deleteTask(@PathVariable("taskId") Long id) {
        this.taskService.deleteTask(id);
    }
}
