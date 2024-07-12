package com.example.demo_spring.services;

import com.example.demo_spring.models.User;

public interface UserRepository {
    User findByUsername(String username);
}
