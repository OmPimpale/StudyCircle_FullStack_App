package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Tutor;
import com.studycircle.studycircle.model.User; // Import User
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Import Repository

import java.util.List;
import java.util.Optional; // Import Optional

@Repository // Add Repository annotation
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    // Add the method to find tutors by subject name
    List<Tutor> findBySubjectsName(String subjectName);

    // Add the method to find a Tutor by the associated User
    Optional<Tutor> findByUser(User user);

    // Or if you map by user ID directly:
    // Optional<Tutor> findByUserId(Long userId);

    // You might also need other custom query methods for tutors here
}
