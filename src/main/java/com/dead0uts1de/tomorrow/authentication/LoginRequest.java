package com.dead0uts1de.tomorrow.authentication;

import lombok.Data;

public record LoginRequest(String email, String password) {
}
