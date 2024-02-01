package com.dead0uts1de.tomorrow.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    // TODO create authentication provider
//    private final AuthenticationProvider authenticationProvider;

//    public SecurityConfiguration(AuthenticationProvider authenticationProvider) {
//        this.authenticationProvider = authenticationProvider;
//    }


    public SecurityConfiguration() {
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("api/v1/auth/**") // TODO implement authentication
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        );
//                .authenticationProvider(authenticationProvider);

        return http.build();
    }
}
