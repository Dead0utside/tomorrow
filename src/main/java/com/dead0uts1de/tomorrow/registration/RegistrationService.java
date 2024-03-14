package com.dead0uts1de.tomorrow.registration;

import com.dead0uts1de.tomorrow.user.User;
import com.dead0uts1de.tomorrow.user.UserRole;
import com.dead0uts1de.tomorrow.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final UserService userService;
    private final EmailValidator emailValidator;
//    private final ConfirmationTokenService confirmationTokenService;
//    private final EmailSender emailSender;

    public String register(RegistrationRequest request) {
        if (!emailValidator.test(request.email())) {
            throw new IllegalStateException("invalid email");
        }

        String token = userService.signUpUser(
                new User(
                        request.name(), // since RegistrationRequest is a record, there are no getters. Therefore, name, email and password are received this way
                        request.email(),
                        request.password(),
                        UserRole.USER
                )
        );
        String link = "http://localhost:8080/api/v1/registration/confirm?token=";

        return "login";
    }

    // TODO implement email confirmation
//    public String confirmToken(String token) {}
}
