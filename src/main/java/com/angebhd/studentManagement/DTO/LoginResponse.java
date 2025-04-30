package com.angebhd.studentManagement.DTO;

import com.angebhd.studentManagement.model.Staff;
import com.angebhd.studentManagement.model.Student;

import lombok.Getter;

@Getter
public class LoginResponse {

    private String token;
    private String role;
    private boolean success;
    private String message;
    private UserData user;

    public LoginResponse(boolean success, String token, String role, String message) {
        this.token = token;
        this.role = role;
        this.success = success;
        this.message = message;

    }

    public LoginResponse(boolean success, String token, String role, String message, Student st) {
        this.token = token;
        this.role = role;
        this.success = success;
        this.message = message;
        this.user = new UserData(st);

    }

    public LoginResponse(boolean success, String token, String role, String message, Staff st) {
        this.token = token;
        this.role = role;
        this.success = success;
        this.message = message;
        this.user = new UserData(st);

    }
}
