package com.example.demo_spring.serviceInterface;

import com.example.demo_spring.dtos.AuthenticationDto;
import com.example.demo_spring.dtos.RegisterDto;
import com.example.demo_spring.models.User;
import com.example.demo_spring.utils.AuthenticationResponse;

public interface AuthenticationInterface {

    AuthenticationResponse register(RegisterDto request);
    AuthenticationResponse login(AuthenticationDto request);

}
