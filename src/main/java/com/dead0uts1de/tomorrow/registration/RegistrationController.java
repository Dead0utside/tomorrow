package com.dead0uts1de.tomorrow.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/register")
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
    // TODO uncomment after implementing email activation
//
//    @GetMapping(path = "confirm")
//    public String confirm(@RequestParam("token") String token) {
//        return registrationService.confirmToken(token);
//    }
}
