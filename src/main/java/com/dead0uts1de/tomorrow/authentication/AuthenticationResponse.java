package com.dead0uts1de.tomorrow.authentication;

public class AuthenticationResponse {
    private final String accessToken;
    private String tokenType;

    public AuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer ";
    }
}
