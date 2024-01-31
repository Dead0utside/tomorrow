package com.dead0uts1de.tomorrow.task;

import com.dead0uts1de.tomorrow.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;

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
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "creation_date")
    private LocalDate creationDate;
//    private TaskType type;
    private LocalDate deadline;
    @ManyToOne
    @JoinColumn(
            name = "application_user_id",
//            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "user_task_fk")
    )
    private User user;

    public Task() { // do I need an empty constructor?
    }

    public Task(String name, String description, LocalDate deadline) {
        this.name = name;
        this.description = description;
        this.creationDate = LocalDate.now();
        this.deadline = deadline;
    }

    public Task(String name) {
        this.name = name;
        this.creationDate = LocalDate.now();
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

//    public TaskType getType() {
//        return type;
//    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
