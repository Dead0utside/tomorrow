package com.dead0uts1de.tomorrow.user;

import com.dead0uts1de.tomorrow.task.Task;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "application_user") // name "user" is reserved and cannot be used for the table
public class User implements UserDetails {
    // TODO make spring data jpa ignore password, locked and enabled attributes (if possible)
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
    @Column(name = "name", nullable = false)
    @Getter
    @Setter
    private String name;
    @Column(name = "email", nullable = false)
    @Getter
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Boolean locked;
    private Boolean enabled;
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Task> tasks = new ArrayList<>();

    public User(String name, String email, String password, UserRole userRole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.locked = false;
        this.enabled = false;
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
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singleton(authority); // TODO I'll figure this out later if needed
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
        return !this.locked; // TODO implement account locking (not sure I need this in the context of this project)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // TODO implement credentials expiration (not sure I need this in the context of this project)
    }

    @Override
    public boolean isEnabled() {
        return this.enabled; // TODO implement account enabling/disabling
    }
}
