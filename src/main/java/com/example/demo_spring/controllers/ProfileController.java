package com.example.demo_spring.controllers;

import com.example.demo_spring.models.Profile;
import com.example.demo_spring.services.ProfileService;
import com.example.demo_spring.utils.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileResponse> getAllProfiles() {
        return profileService.findAllProfile();
    }

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
