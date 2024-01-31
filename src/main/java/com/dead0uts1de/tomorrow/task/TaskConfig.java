package com.dead0uts1de.tomorrow.task;

import com.dead0uts1de.tomorrow.user.UserController;
import com.dead0uts1de.tomorrow.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class TaskConfig {
    @Bean
    CommandLineRunner TaskConfigurationCLR (TaskRepository taskRepository, UserService userService) {
        return args -> {
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
