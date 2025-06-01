package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be a Spring Data JPA repository for the Student entity.
public interface StudentRepository extends JpaRepository<Student, Long> {
}