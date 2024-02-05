package com.dead0uts1de.tomorrow.configuration.authentication;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
}
