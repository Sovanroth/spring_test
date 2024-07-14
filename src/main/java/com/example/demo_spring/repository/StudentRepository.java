package com.example.demo_spring.repository;

import com.example.demo_spring.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
