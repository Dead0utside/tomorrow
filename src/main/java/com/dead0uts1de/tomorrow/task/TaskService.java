package com.dead0uts1de.tomorrow.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTaskById(Long id) {
        return this.taskRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("task with id " + " not found")
        );
    }

    public List<Task> getTasks() {
        return this.taskRepository.findAll();
    }

    public void createTask(Task task) {
        // TODO create some business logic if required
        // for some reason the creation date is not set automatically to NOW via the constructor
        // when the creation date parameter is not in request body. Therefore creation date must be
        // explicitly assigned here
        task.setCreationDate(LocalDate.now());
        this.taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        this.taskRepository.deleteById(id);
    }
}
