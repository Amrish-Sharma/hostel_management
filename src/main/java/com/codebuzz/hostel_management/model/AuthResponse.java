package com.codebuzz.hostel_management.model;

import java.util.Date;

public class AuthResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    private String tokenType = "Bearer";
    private Date expiresAt;

    public AuthResponse(String token, Date expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }
}
