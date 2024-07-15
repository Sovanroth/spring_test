package com.example.demo_spring.utils;

import com.example.demo_spring.models.Profile;
import java.util.List;

public class ProfileResponse {
    private boolean error;
    private String message;
    private List<Profile> profiles;

    public ProfileResponse(boolean error, String message, List<Profile> profiles) {
        this.error = error;
        this.message = message;
        this.profiles = profiles;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }
}
