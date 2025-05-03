package com.angebhd.studentManagement.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angebhd.studentManagement.DTO.LoginRequest;
import com.angebhd.studentManagement.DTO.LoginResponse;
import com.angebhd.studentManagement.service.AuthenticationService;
import com.angebhd.studentManagement.service.JWTUtilities;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JWTUtilities jwtUtilities;

    @Value("${front.end.ip}")
    private String frontEndIp;

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        LoginResponse res = authenticationService.login(loginRequest);
        if (res.isSuccess()) {
            return new ResponseEntity<>(res, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(res, HttpStatusCode.valueOf(401));
        }
    }

    @GetMapping(value = "oauth2/success")
    public void oauth2LoginSuccess(HttpServletResponse response, HttpServletRequest request,
            Authentication authentication) throws IOException {
        try {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

            // Extract username (email or login)
            String username = oauth2User.getAttribute("email") != null
                    ? oauth2User.getAttribute("email")
                    : oauth2User.getAttribute("login");

            String token = jwtUtilities.generateToken(username);

            // Set the token in the Authorization header
            response.addCookie(new Cookie("token", token) {
                {
                    setHttpOnly(true); // Prevent access from JavaScript
                    setSecure(true); // Only send over HTTPS
                    setPath("/"); // Cookie available to the entire domain
                }
            });
            response.sendRedirect(frontEndIp + "/auth/oauth2/success");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            response.sendRedirect(frontEndIp + "/auth/oauth2/failed");
        }
    }

}