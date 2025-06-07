package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional; // Import Optional

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    // Add method to find a Subject by name
    Optional<Subject> findByName(String name);

    // You might need other custom query methods for subjects here
}
