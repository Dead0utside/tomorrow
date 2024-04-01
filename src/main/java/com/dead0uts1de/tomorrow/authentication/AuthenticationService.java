package com.dead0uts1de.tomorrow.authentication;

import com.dead0uts1de.tomorrow.security.jwt.JwtGenerator;
import com.dead0uts1de.tomorrow.security.jwt.JwtService;
import com.dead0uts1de.tomorrow.user.User;
import com.dead0uts1de.tomorrow.user.UserRole;
import com.dead0uts1de.tomorrow.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final EmailValidator emailValidator;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;


//    private final ConfirmationTokenService confirmationTokenService;
//    private final EmailSender emailSender;

    public void register(RegistrationRequest request) {
        if (!emailValidator.test(request.email())) {
            throw new IllegalStateException("invalid email");
        }

        User user = User.builder()
                .name(request.email().substring(0, request.email().indexOf("@")))
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .userRole(UserRole.USER)
                .locked(false)
                .enabled(true)
                .build();

        userService.signUpUser(user);
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(), request.password()
                )
        );

        User user = userService.getUserByEmail(request.email());
        String jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken);
    }

    // TODO implement email confirmation
//    public String confirmToken(String token) {}
}
