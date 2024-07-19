package com.example.demo_spring.utils;

import com.example.demo_spring.models.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomResponse {
    private Boolean error;
    private String message;
    private List<Student> students;

    public CustomResponse(boolean error, String message, List<Student> students) {
        this.error = error;
        this.message = message;
        this.students = students;
    }

}
