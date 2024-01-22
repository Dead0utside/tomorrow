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

    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(); // create custom exception
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }
    // requests here
}
