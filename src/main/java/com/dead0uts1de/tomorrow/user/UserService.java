package com.dead0uts1de.tomorrow.user;

import jakarta.transaction.Transactional;
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
        return this.userRepository.findById(id).orElseThrow(() -> new IllegalStateException("user with id " + id + " not found")); // TODO create custom exception
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public void addUser(User user) {
        if (this.userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("this email is already used by another user");
        }

        this.userRepository.save(user);
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
}
