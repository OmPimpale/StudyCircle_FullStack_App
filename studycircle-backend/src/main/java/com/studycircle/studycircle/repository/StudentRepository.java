package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}