package com.example.demo_spring.serviceInterface;

import com.example.demo_spring.dtos.StudentDto;
import com.example.demo_spring.models.Student;
import com.example.demo_spring.utils.CustomResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface StudentInterface {

    ResponseEntity<CustomResponse> getAllStudents();

    ResponseEntity<CustomResponse> createStudent(StudentDto student);

    ResponseEntity<CustomResponse> deleteStudent(int id);

    ResponseEntity<CustomResponse> updateStudent(int id, StudentDto student);

    ResponseEntity<CustomResponse> searchStudentsByName(String name);

    Optional<Student> getStudentById(int id);
}
