package com.dead0uts1de.tomorrow.task;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks() {
        return this.taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return this.taskRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("task with id " + " not found")
        );
    }
}
