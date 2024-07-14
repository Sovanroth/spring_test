package com.example.demo_spring.services;

import com.example.demo_spring.models.Profile;
import com.example.demo_spring.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Profile updateProfile(int id, Profile profileDetails) {
        return profileRepository.findById(id).map(profile -> {
            profile.setAddress(profileDetails.getAddress());
            profile.setPhoneNumber(profileDetails.getPhoneNumber());
            return profileRepository.save(profile);
        }).orElseGet(() -> {
            profileDetails.setId(id);
            return profileRepository.save(profileDetails);
        });
    }

    public void deleteProfile(int id) {
        profileRepository.deleteById(id);
    }

}
