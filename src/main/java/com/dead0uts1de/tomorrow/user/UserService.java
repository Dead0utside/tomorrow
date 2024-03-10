package com.dead0uts1de.tomorrow.user;

import com.dead0uts1de.tomorrow.registration.token.ConfirmationToken;
import com.dead0uts1de.tomorrow.registration.token.ConfirmationTokenService;
import com.dead0uts1de.tomorrow.task.Task;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
//    private final static String USER_NOT_FOUND_MSG = "user with this email does not exist";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    // requests here

    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new IllegalStateException("user with id " + id + " not found")); // TODO create custom exception
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user with this email not found"));
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public String signUpUser(User user) {
        if (this.userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            // TODO if email is not confirmed send another confirmation email
            throw new IllegalStateException("email already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(UUID.randomUUID().toString(), LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
        enableUser(user.getEmail());
        confirmationToken.setConfirmedAt(LocalDateTime.now()); // TODO remove this line when activation per email is implemented
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return confirmationToken.getToken();
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
