package com.dead0uts1de.tomorrow;

import com.dead0uts1de.tomorrow.security.PasswordEncoder;
import com.dead0uts1de.tomorrow.task.Task;
import com.dead0uts1de.tomorrow.task.TaskRepository;
import com.dead0uts1de.tomorrow.user.User;
import com.dead0uts1de.tomorrow.user.UserRepository;
import com.dead0uts1de.tomorrow.user.UserRole;
import com.dead0uts1de.tomorrow.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class InitDatabase {
    @Bean
    CommandLineRunner UserConfigurationCLR (UserRepository userRepository, TaskRepository taskRepository, UserService userService, PasswordEncoder passwordEncoder) { // this is used to automatically create a user on application startup
        return args -> {
            User geralt = new User("Geralt", "geralt@mail.com", "geraltpasswd", UserRole.USER);
            User lambert = new User("Lambert", "lambert@mail.com", "lambertpasswd", UserRole.USER);
            User eskel = new User("Eskel", "eskel@mail.com", "eskelpasswd", UserRole.USER);
//            repository.saveAll(List.of(geralt, lambert, eskel));
            userService.signUpUser(geralt);
            userService.signUpUser(lambert);
            userService.signUpUser(eskel);

            Task firstTask = new Task("Conquer the world", LocalDate.now().minusYears(10));
            Task secondTask = new Task("Learn all languages", LocalDate.now().minusYears(12));
            Task thirdTask = new Task("Complete latest contract", LocalDate.now().minusDays(3));
            Task fourthTask = new Task("Help Vesemir clean up the castle", LocalDate.now());
            Task fifthTask = new Task("Find Ciri", LocalDate.now().minusMonths(4));
            Task sixthTask = new Task("Collect all Gwent cards", LocalDate.now().minusMonths(3));

            userService.addTasks(1L, List.of(thirdTask, fifthTask, sixthTask));
            userService.addTasks(2L, List.of(firstTask));
            userService.addTasks(3L, List.of(secondTask, fourthTask));

            taskRepository.saveAll(List.of(firstTask, secondTask, thirdTask, fourthTask, fifthTask, sixthTask));
        };
    }
}
