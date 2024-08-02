package com.example.demo_spring.services;

import com.example.demo_spring.dtos.StudentDto;
import com.example.demo_spring.models.Student;
import com.example.demo_spring.repository.StudentRepository;
import com.example.demo_spring.serviceInterface.StudentInterface;
import com.example.demo_spring.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService implements StudentInterface {

    private final StudentRepository studentRepository;

    @Override
    public ResponseEntity<CustomResponse> getAllStudents() {
        try {
            List<Student> students = studentRepository.findAll();
            return ResponseEntity.ok(new CustomResponse(false, "Get Successfully", students));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse(true, "Error getting data", null));
        }
    }

    @Override
    public ResponseEntity<CustomResponse> createStudent(@RequestBody StudentDto student) {
        try {
            if (student.getName().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new CustomResponse(true, "Please input student name", null));
            } else {
                Student studentData = new Student();
                studentData.setName(student.getName());
                studentData.setAge(student.getAge());
                Student savedStudent = studentRepository.save(studentData);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new CustomResponse(false, "Student created successfully", List.of(savedStudent)));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse(true, "Error creating student", null));
        }
    }

    @Override
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

    @Override
    public ResponseEntity<CustomResponse> updateStudent(int id, StudentDto student) {
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

    @Override
    public ResponseEntity<CustomResponse> searchStudentsByName(String name) {
        try {
            List<Student> students = studentRepository.findByNameContainingIgnoreCase(name);
            return ResponseEntity.ok(new CustomResponse(false, "Get Successfully", students));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse(true, "Error searching students", null));
        }
    }

    @Override
    public Optional<Student> getStudentById(int id) {
        return studentRepository.findById(id);
    }

}
