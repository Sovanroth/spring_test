package com.example.demo_spring.controllers;

import com.example.demo_spring.models.Profile;
import com.example.demo_spring.models.Student;
import com.example.demo_spring.services.ProfileService;
import com.example.demo_spring.services.StudentService;
import com.example.demo_spring.utils.CustomResponse;
import com.example.demo_spring.utils.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final StudentService studentService;


    @PostMapping("/{studentId}")
    public ResponseEntity<ProfileResponse> createProfile(@PathVariable int studentId, @RequestBody Profile profile) {
        return profileService.createProfile(studentId, profile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponse> updateProfile(@PathVariable int id, @RequestBody Profile profile) {
        return profileService.updateProfile(id, profile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProfileResponse> deleteProfile(@PathVariable int id) {
        return profileService.deleteProfile(id);
    }

}
