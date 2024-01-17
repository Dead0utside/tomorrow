package com.dead0uts1de.tomorrow.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) { // this is used to automatically create a user on application startup
        return args -> {
            User geralt = new User("Geralt", "geralt@mail.com");
            repository.saveAll(List.of(geralt));
        };
    }
}
