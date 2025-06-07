package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Student;
import com.studycircle.studycircle.model.User; // Import User
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Import Repository

import java.util.Optional; // Import Optional

// This will be a Spring Data JPA repository for the Student entity.
@Repository // Add Repository annotation
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Add the method to find a Student by the associated User
    Optional<Student> findByUser(User user);

    // Or if you map by user ID directly:
    // Optional<Student> findByUserId(Long userId);
}
