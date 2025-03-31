package com.angebhd.studentManagement.DTO;

import lombok.Getter;

@Getter
public class LoginResponse {

    private String token;
    private String role;
    private boolean success;
    private String message;

    public LoginResponse(boolean success, String token, String role, String message) {
        this.token = token;
        this.role = role;
        this.success = success;
        this.message = message;
    }
}
