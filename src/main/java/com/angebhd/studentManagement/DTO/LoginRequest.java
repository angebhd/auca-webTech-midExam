package com.angebhd.studentManagement.DTO;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String username;
    private String password;
    private String loginAs;
}