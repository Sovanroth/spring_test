package com.example.demo_spring.controllers;

import com.example.demo_spring.models.CustomResponse;
import com.example.demo_spring.models.Student;
import com.example.demo_spring.services.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CustomResponse> getAllStudents() {
        try {
            List<Student> students = studentRepository.findAll();
            return ResponseEntity.ok(new CustomResponse(false, "Get Successfully", students));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse(true, "Error retrieving data", null));
        }
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createStudent(@RequestBody Student student) {
        try {
            Student savedStudent = studentRepository.save(student);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CustomResponse(false, "Student created successfully", List.of(savedStudent)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse(true, "Error creating student", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteStudent(@PathVariable int id) {
        try {
            Optional<Student> studentOptional = studentRepository.findById(id);
            if (studentOptional.isPresent()) {
                studentRepository.deleteById(id);
//                return ResponseEntity.ok(new CustomResponse(false, "Student deleted successfully", List.of(studentOptional.get())));
                return ResponseEntity.ok(new CustomResponse(false, "Student deleted successfully", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse(true, "Student not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse(true, "Error deleting student", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateStudent(@PathVariable int id, @RequestBody Student student) {
        try {
            Optional<Student> studentOptional = studentRepository.findById(id);
            if (studentOptional.isPresent()) {
                Student existingStudent = studentOptional.get();

                if (student.getName() != null) {
                    existingStudent.setName(student.getName());
                }
                if (student.getAge() != 0) {
                    existingStudent.setAge(student.getAge());
                }

                Student updatedStudent = studentRepository.save(existingStudent);
                return ResponseEntity.ok(new CustomResponse(false, "Student updated successfully", List.of(updatedStudent)));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse(true, "Student not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse(true, "Error updating student", null));
        }
    }
}
