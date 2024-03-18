package com.dead0uts1de.tomorrow.authentication;

import com.dead0uts1de.tomorrow.security.PasswordEncoder;
import com.dead0uts1de.tomorrow.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

//    @PostMapping("/register")
//    public String register(@RequestBody RegistrationRequest request) {
//        return authenticationService.register(request);
//    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        System.out.println("calling service register from controller");
        return authenticationService.register(request);
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        return authenticationService.login(request);
    }
}
