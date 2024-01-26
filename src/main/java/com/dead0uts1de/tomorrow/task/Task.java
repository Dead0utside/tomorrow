package com.dead0uts1de.tomorrow.task;

import com.dead0uts1de.tomorrow.user.User;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
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

    public Task(String name, LocalDate creationDate) {
        this.name = name;
        this.creationDate = creationDate;
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
