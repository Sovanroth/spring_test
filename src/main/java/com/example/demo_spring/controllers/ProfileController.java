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

    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponse> updateProfile(@PathVariable int id, @RequestBody Profile profile) {
        ProfileResponse response = profileService.updateProfile(id, profile);
        return new ResponseEntity<>(response, response.isError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProfileResponse> deleteProfile(@PathVariable int id) {
        return profileService.deleteProfile(id);
    }

}
