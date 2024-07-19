package com.example.demo_spring.services;

import com.example.demo_spring.config.JwtService;
import com.example.demo_spring.models.User;
import com.example.demo_spring.repository.UserRepository;
import com.example.demo_spring.dtos.AuthenticationDto;
import com.example.demo_spring.serviceInterface.AuthenticationInterface;
import com.example.demo_spring.utils.AuthenticationResponse;
import com.example.demo_spring.dtos.RegisterDto;
import com.example.demo_spring.utils.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationInterface {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse register(RegisterDto request) {

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
                    .user(UserResponse.builder()
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


    @Override
    public AuthenticationResponse login(AuthenticationDto request) {
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
                    .user(UserResponse.builder()
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
