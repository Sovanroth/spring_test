package com.example.demo_spring.utils;

import com.example.demo_spring.models.Profile;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProfileResponse {
    private boolean error;

    private String message;
    private List<Profile> profiles;

    public ProfileResponse(boolean error, String message, List<Profile> profiles) {
        this.error = error;
        this.message = message;
        this.profiles = profiles;
    }

}
