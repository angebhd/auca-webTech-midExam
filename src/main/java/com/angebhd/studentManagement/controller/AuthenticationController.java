package com.angebhd.studentManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angebhd.studentManagement.DTO.LoginRequest;
import com.angebhd.studentManagement.DTO.LoginResponse;
import com.angebhd.studentManagement.service.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        LoginResponse res = authenticationService.login(loginRequest);
        if (res.isSuccess()) {
            return new ResponseEntity<>(res, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(res, HttpStatusCode.valueOf(401));
        }
    }


}