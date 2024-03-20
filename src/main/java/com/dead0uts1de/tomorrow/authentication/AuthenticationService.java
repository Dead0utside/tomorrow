package com.dead0uts1de.tomorrow.authentication;

import com.dead0uts1de.tomorrow.security.jwt.JWTGenerator;
import com.dead0uts1de.tomorrow.user.User;
import com.dead0uts1de.tomorrow.user.UserRole;
import com.dead0uts1de.tomorrow.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserService userService;
    private final EmailValidator emailValidator;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator tokenGenerator;

//    private final ConfirmationTokenService confirmationTokenService;
//    private final EmailSender emailSender;

    @Autowired
    public AuthenticationService(UserService userService, EmailValidator emailValidator, AuthenticationManager authenticationManager, JWTGenerator tokenGenerator) {
        this.userService = userService;
        this.emailValidator = emailValidator;
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
    }

    public ResponseEntity<String> register(RegistrationRequest request) {
        if (!emailValidator.test(request.email())) {
            throw new IllegalStateException("invalid email");
        }

        User user = User.builder()
                .name(request.email().substring(0, request.email().indexOf("@")))
                .email(request.email())
                .password(request.password())
                .userRole(UserRole.USER)
                .enabled(true)
                .locked(false)
                .build();

        return userService.signUpUser(user);
    }

    public ResponseEntity<AuthenticationResponse> login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(), request.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication);

        return new ResponseEntity<>(new AuthenticationResponse(token, "Bearer "), HttpStatus.OK);
    }

    // TODO implement email confirmation
//    public String confirmToken(String token) {}
}
