package com.dead0uts1de.tomorrow.security.config;

import com.dead0uts1de.tomorrow.security.jwt.JWTAuthenticationFilter;
import com.dead0uts1de.tomorrow.security.jwt.JWTGenerator;
import com.dead0uts1de.tomorrow.security.jwt.JwtAuthEntryPoint;
import com.dead0uts1de.tomorrow.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final UserService userService;
    private final JwtAuthEntryPoint authEntryPoint;
    private final JWTGenerator jwtGenerator;

    @Autowired
    public WebSecurityConfig(UserService userService, JwtAuthEntryPoint authEntryPoint, JWTGenerator jwtGenerator) {
        this.userService = userService;
        this.authEntryPoint = authEntryPoint;
        this.jwtGenerator = jwtGenerator;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authEntryPoint)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v*/authentication/**")
                        .permitAll()
                        .requestMatchers("/register/**")
                        .permitAll()
//                        .requestMatchers("/api/v*/users/get-authorized-username/**")
//                        .permitAll()
                        .requestMatchers("/js/**")
                        .denyAll()
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(auth -> auth
                        .authenticationEntryPoint(authEntryPoint)
                );

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter(jwtGenerator, userService);
    }
}
