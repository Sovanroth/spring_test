package com.example.demo_spring.controllers;

import com.example.demo_spring.models.CustomResponse;
import com.example.demo_spring.models.Student;
import com.example.demo_spring.services.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public CustomResponse getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return new CustomResponse(false, "Data retrieval successful", students);
    }

    @PostMapping
    public CustomResponse createStudent(@RequestBody Student student) {
        Student savedStudent = studentRepository.save(student);
        return new CustomResponse(false, "Student created successfully", List.of(savedStudent));
    }

    @DeleteMapping("/{id}")
    public CustomResponse deleteStudent(@PathVariable int id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            studentRepository.deleteById(id);
            return new CustomResponse(false, "Student deleted successfully", List.of(studentOptional.get()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
    }






}
