package com.dead0uts1de.tomorrow.user;

import java.util.List;

public class User {
    private Long id;
    private String name;
    private String email;
    // TODO create Task class
//    private List<Task> tasks;


    public User() { // do I actually need it?
    }

    public User(Long id, String name, String email) {
        this.id = id;
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
