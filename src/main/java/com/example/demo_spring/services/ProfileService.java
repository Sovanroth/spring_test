package com.example.demo_spring.services;

import com.example.demo_spring.models.Profile;
import com.example.demo_spring.repository.ProfileRepository;
import com.example.demo_spring.utils.CustomResponse;
import com.example.demo_spring.utils.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertyResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public List<Profile> findAllProfile() {
        return profileRepository.findAll();
    }

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Optional<Profile> getProfileById(int id) {
        return profileRepository.findById(id);
    }

    public ProfileResponse updateProfile(int id, Profile profile) {
        try {
            Optional<Profile> profileOptional = profileRepository.findById(id);
            if (profileOptional.isPresent()) {
                Profile existingProfile = profileOptional.get();

                if (profile.getAddress() != null) {
                    existingProfile.setAddress(profile.getAddress());
                }

                if (profile.getPhoneNumber() != null) {
                    existingProfile.setPhoneNumber(profile.getPhoneNumber());
                }

                Profile updatedProfile = profileRepository.save(existingProfile);
                return new ProfileResponse(false, "Profile updated successfully", List.of(updatedProfile));
            } else {
                return new ProfileResponse(true, "Profile not found", null);
            }
        } catch (Exception e) {
            return new ProfileResponse(true, "Error updating profile", null);
        }
    }

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
