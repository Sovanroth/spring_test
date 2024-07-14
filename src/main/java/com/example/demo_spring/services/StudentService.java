package com.example.demo_spring.services;

import com.example.demo_spring.models.Student;
import com.example.demo_spring.repository.StudentRepository;
import com.example.demo_spring.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ResponseEntity<CustomResponse> getAllStudents() {
        try {
            List<Student> students = studentRepository.findAll();
            return ResponseEntity.ok(new CustomResponse(false, "Get Successfully", students));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse(true, "Error retrieving data", null));
        }
    }

    public ResponseEntity<CustomResponse> createStudent(Student student) {
        try {
            Student savedStudent = studentRepository.save(student);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CustomResponse(false, "Student created successfully", List.of(savedStudent)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse(true, "Error creating student", null));
        }
    }

    public ResponseEntity<CustomResponse> deleteStudent(int id) {
        try {
            Optional<Student> studentOptional = studentRepository.findById(id);
            if (studentOptional.isPresent()) {
                studentRepository.deleteById(id);
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

    public ResponseEntity<CustomResponse> updateStudent(int id, Student student) {
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
