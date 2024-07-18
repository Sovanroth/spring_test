package com.example.demo_spring.services;

import com.example.demo_spring.config.JwtService;
import com.example.demo_spring.models.User;
import com.example.demo_spring.repository.UserRepository;
import com.example.demo_spring.utils.AuthenticationRequest;
import com.example.demo_spring.utils.AuthenticationResponse;
import com.example.demo_spring.utils.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
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
            if (repository.findByEmail(request.getEmail()).isPresent()) {
                return AuthenticationResponse.builder()
                        .error(true)
                        .message("Email is already in use")
                        .build();
            }

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


    public AuthenticationResponse login(AuthenticationRequest request) {
        try {
            var userOptional = repository.findByEmail(request.getEmail());
            if (userOptional.isEmpty()) {
                return AuthenticationResponse.builder()
                        .error(true)
                        .message("Invalid credentials")
                        .build();
            }

            var user = userOptional.get();

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return AuthenticationResponse.builder()
                        .error(true)
                        .message("Invalid credentials")
                        .build();
            }

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
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .error(true)
                    .message("An unexpected error occurred: " + e.getMessage())
                    .build();
        }
    }

}
