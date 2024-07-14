package com.example.demo_spring.utils;

import com.example.demo_spring.config.JwtService;
import com.example.demo_spring.models.User;
import com.example.demo_spring.services.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {

        try {
            // Check if the email is already in use
            if (repository.findByEmail(request.getEmail()).isPresent()) {
                return AuthenticationResponse.builder()
                        .error(true)
                        .message("Email is already in use")
                        .build();
            }

            // If email is not in use, proceed with registration
            var user = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .error(false)
                    .message("User registered successfully")
                    .user(AuthenticationResponse.UserResponse.builder()
                            .email(user.getEmail())
                            .password(user.getPassword())
                            .token(jwtToken)
                            .build())
                    .build();
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .error(true)
                    .message("An unexpected error occurred: " + e.getMessage())
                    .build();
        }
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = repository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .error(false)
                .message("Login successful")
                .user(AuthenticationResponse.UserResponse.builder()
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .token(jwtToken)
                        .build())
                .build();
    }
}
