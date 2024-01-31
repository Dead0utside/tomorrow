package com.dead0uts1de.tomorrow.user;

import com.dead0uts1de.tomorrow.task.Task;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "application_user") // name "user" is reserved and cannot be used for the table
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;
    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Task> tasks = new ArrayList<>();


    public User() { // do I actually need it?
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Now this is probably not the smartest way to connect Tasks with Users.
    // I will have to revisit this issue once security is implemented.
    // It might be possible to get the currently authorised user out of the context
    public void addTask(Task task) {
        if (!this.tasks.contains(task)) {
            this.tasks.add(task);
            task.setUser(this);
        }
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
        task.setUser(null);
    }
}
