package com.example.demo_spring.services;

import com.example.demo_spring.dtos.ProfileDto;
import com.example.demo_spring.models.Profile;
import com.example.demo_spring.models.Student;
import com.example.demo_spring.repository.ProfileRepository;
import com.example.demo_spring.serviceInterface.ProfileInterface;
import com.example.demo_spring.utils.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService implements ProfileInterface {

    private final ProfileRepository profileRepository;
    private final StudentService studentService;

    @Override
    public ResponseEntity<ProfileResponse> findAllProfile() {
        try {
            List<Profile> profiles = profileRepository.findAll();
            return ResponseEntity.ok(new ProfileResponse(false, "Get Successfully", profiles));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProfileResponse(true, "Error getting data", null));
        }
    }

    @Override
    public ResponseEntity<ProfileResponse> createProfile(int studentId, ProfileDto profile) {
        try {
            Optional<Student> studentOptional = studentService.getStudentById(studentId);
            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();

                Profile profileData = new Profile();
                profileData.setAddress(profile.getAddress());
                profileData.setPhoneNumber(profile.getPhoneNumber());
                profileData.setStudent(student);

                Profile createdProfile = profileRepository.save(profileData);

                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ProfileResponse(false, "Profile created successfully", List.of(createdProfile)));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ProfileResponse(true, "Student not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProfileResponse(true, "Error creating profile", null));
        }
    }

    @Override
    public ResponseEntity<ProfileResponse> updateProfile(int id, ProfileDto profile) {
        try {
            Optional<Profile> existingProfileOptional = profileRepository.findById(id);
            if (existingProfileOptional.isPresent()) {
                Profile existingProfile = existingProfileOptional.get();

                if (profile.getAddress() != null) {
                    existingProfile.setAddress(profile.getAddress());
                }

                if (profile.getPhoneNumber() != null) {
                    existingProfile.setPhoneNumber(profile.getPhoneNumber());
                }

                Profile updatedProfile = profileRepository.save(existingProfile);
                return ResponseEntity.ok(new ProfileResponse(false, "Profile updated successfully", List.of(updatedProfile)));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ProfileResponse(true, "Profile not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProfileResponse(true, "Error updating profile", null));
        }
    }

    @Override
    public ResponseEntity<ProfileResponse> deleteProfile(int id) {
        try {
            Optional<Profile> profileOptional = profileRepository.findById(id);
            if (profileOptional.isPresent()){
                profileRepository.deleteById(id);
                System.out.println(id);
                return ResponseEntity.ok(new ProfileResponse(true, "Profile deleted successfully", null));
            } else {
                System.out.println("No profile found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ProfileResponse(false, "Profile not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProfileResponse(true, "Error deleting profile", null));
        }
    }

}
