package com.example.demo_spring.models;

import java.util.List;

public class CustomResponse {
    private boolean error;
    private String message;
    private List<Student> students;

    public CustomResponse(boolean error, String message, List<Student> students) {
        this.error = error;
        this.message = message;
        this.students = students;
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
