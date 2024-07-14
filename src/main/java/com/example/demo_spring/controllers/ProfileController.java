package com.example.demo_spring.controllers;

import com.example.demo_spring.models.Profile;
import com.example.demo_spring.models.Student;
import com.example.demo_spring.services.ProfileService;
import com.example.demo_spring.services.StudentService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileService.findAllProfile();
    }

    @PostMapping("/{studentId}")
    public ResponseEntity<Profile> createProfile(@PathVariable int studentId, @RequestBody Profile profile) {
        Optional<Student> studentOptional = studentService.getStudentById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            profile.setStudent(student);
            Profile createdProfile = profileService.createProfile(profile);
            // Fetch the complete profile including associated student data
            Optional<Profile> fullProfile = profileService.getProfileById(createdProfile.getId());
            return fullProfile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
