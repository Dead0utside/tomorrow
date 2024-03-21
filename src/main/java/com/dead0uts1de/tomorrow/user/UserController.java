package com.dead0uts1de.tomorrow.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "{userId}")
    public User getUserById(@PathVariable("userId") Long id) {
        return this.userService.getUserById(id);
    }

    @GetMapping
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        this.userService.signUpUser(user);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable Long userId) {
        this.userService.deleteUser(userId);
    }

    @PutMapping(path = "{userId}")
    public void changeName(@PathVariable("userId") Long userId, @RequestBody String newName) { // the request body looks kinda weird now. Maybe I should just use @RequestParam instead of @RequestBody
        this.userService.changeName(userId, newName);
    }

    @GetMapping(path = "get-authorized-username")
    public String getAuthorizedUsername() {
        String authorizedEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(authorizedEmail).getName();
    }

//    @PutMapping(path = "{userId}")
//    public void addTasks(@PathVariable("userId") Long userId, @RequestBody List<Task> tasks) {
//        this.userService.addTasks(userId, tasks);
//    }

    // TODO assign the created task to user (probably not here but anyway)
}
