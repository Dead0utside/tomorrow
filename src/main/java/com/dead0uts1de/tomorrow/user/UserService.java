package com.dead0uts1de.tomorrow.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // requests here

    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(); // TODO create custom exception
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public void addUser(User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("this email is already used by another user");
        }

        this.userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
