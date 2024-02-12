package com.dead0uts1de.tomorrow.user;

import com.dead0uts1de.tomorrow.task.Task;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
//    private final static String USER_NOT_FOUND_MSG = "user with this email does not exist";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    // requests here

    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new IllegalStateException("user with id " + id + " not found")); // TODO create custom exception
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public String signUpUser(User user) {
        if (this.userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("email already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setEnabled(true); // TODO move this line to when the email is confirmed when possible, also there is enableUser() method for this

        // TODO send confirmation per email

        this.userRepository.save(user);

        return "success";
    }

    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    @Transactional
    public void changeName(Long userId, String newName) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("user with id " + userId + " does not exist"));

//        user.setName(newName.getName()); // this feels wrong but passing new name as a JSON object is OP
        user.setName(newName);
    }

    @Transactional
    public void addTasks(Long userId, List<Task> tasks) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("user with id " + userId + " does not exist"));
        for (Task task : tasks) {
            user.addTask(task);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user with this email does not exist"));
    }
}
