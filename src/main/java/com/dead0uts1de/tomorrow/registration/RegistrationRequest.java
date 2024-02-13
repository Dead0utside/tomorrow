package com.dead0uts1de.tomorrow.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;



public record RegistrationRequest(String name, String email, String password) {
}
