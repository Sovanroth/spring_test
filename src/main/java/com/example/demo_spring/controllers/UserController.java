package com.example.demo_spring.controllers;

import com.example.demo_spring.dtos.AuthenticationDto;
import com.example.demo_spring.utils.AuthenticationResponse;
import com.example.demo_spring.services.AuthenticationService;
import com.example.demo_spring.dtos.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterDto request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationDto request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

}
