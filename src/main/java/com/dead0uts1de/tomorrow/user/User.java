package com.dead0uts1de.tomorrow.user;

import com.dead0uts1de.tomorrow.task.Task;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "application_user") // name "user" is reserved and cannot be used for the table
public class User implements UserDetails {
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
    @Getter
    private Long id;
    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    @Getter
    private String name;
    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    @Getter
    private String email;
    private String password;
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Task> tasks = new ArrayList<>();

    public User(String name, String email) {
        this.name = name;
        this.email = email;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // TODO I'll figure this out later if needed
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email; // in context of UserDetails username is email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // TODO implement account expiration (not sure I need this in the context of this project)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // TODO implement account locking (not sure I need this in the context of this project)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // TODO implement credentials expiration (not sure I need this in the context of this project)
    }

    @Override
    public boolean isEnabled() {
        return true; // TODO implement account enabling/disabling
    }
}
