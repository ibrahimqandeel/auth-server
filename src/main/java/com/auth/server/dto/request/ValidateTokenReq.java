package com.auth.server.dto.request;

public class ValidateTokenReq {

    private final String token;

    public ValidateTokenReq(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
