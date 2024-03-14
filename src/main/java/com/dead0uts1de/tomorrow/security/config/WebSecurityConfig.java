package com.dead0uts1de.tomorrow.security.config;

import com.dead0uts1de.tomorrow.user.UserRole;
import com.dead0uts1de.tomorrow.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v*/registration/**")
                        .permitAll()
                        .requestMatchers("/register/**")
                        .permitAll()
                        .requestMatchers("/api/v*/users/get-authorized-username/**")
                        .permitAll()
//                        .requestMatchers("/js/**")
//                        .denyAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/home", true)
                        // TODO create a custom success handler that could redirect user directly to requested page instead of home
                )
                .authenticationProvider(daoAuthenticationProvider()
                );

        return http.build();
    }

    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}
