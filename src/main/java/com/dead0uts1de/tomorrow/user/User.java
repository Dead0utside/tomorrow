package com.dead0uts1de.tomorrow.user;

import com.dead0uts1de.tomorrow.task.Task;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
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
    private Long id;
    private String name;
    private String email;
    // TODO create a one-to-many relation table, containing task list for every user
//    private List<Task> tasks;


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
}
