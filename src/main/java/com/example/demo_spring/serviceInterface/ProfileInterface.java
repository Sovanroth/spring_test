package com.example.demo_spring.serviceInterface;

import com.example.demo_spring.dtos.ProfileDto;
import com.example.demo_spring.utils.ProfileResponse;
import org.springframework.http.ResponseEntity;

public interface ProfileInterface {

    ResponseEntity<ProfileResponse> findAllProfile();
    ResponseEntity<ProfileResponse> createProfile(int studentId, ProfileDto profile);
    ResponseEntity<ProfileResponse> updateProfile(int id, ProfileDto profile);
    ResponseEntity<ProfileResponse> deleteProfile(int id);
}
