package com.dead0uts1de.tomorrow.task;

import com.dead0uts1de.tomorrow.user.User;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

public class Task {
    private Long id;
    private String name;
    private String description;
    private LocalDate creationDate;
    private TaskType type;
    private LocalDate deadline;
    // TODO establish a many-to-one relation between tasks and user
//    private User user;

    public Task() { // do I need an empty constructor?
    }

    public Task(String name, String description, LocalDate creationDate, TaskType type, LocalDate deadline) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.type = type;
        this.deadline = deadline;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public TaskType getType() {
        return type;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
}
