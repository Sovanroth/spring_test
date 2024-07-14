package com.example.demo_spring.controllers;

import com.example.demo_spring.models.Student;
import com.example.demo_spring.services.StudentService;
import com.example.demo_spring.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<CustomResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteStudent(@PathVariable int id) {
        return studentService.deleteStudent(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateStudent(@PathVariable int id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @GetMapping("/search")
    public ResponseEntity<CustomResponse> searchStudentsByName(@RequestParam String name) {
        return studentService.searchStudentsByName(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
