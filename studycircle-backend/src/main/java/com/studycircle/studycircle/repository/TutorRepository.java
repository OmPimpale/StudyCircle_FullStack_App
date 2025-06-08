package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Tutor;
import com.studycircle.studycircle.model.User; // Import User
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // Import Optional

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    @Query("SELECT t FROM Tutor t WHERE t.subjectsTaught LIKE %:subjectName%")
    List<Tutor> findBySubjectsTaughtContainingIgnoreCase(String subjectName);

    // Add this method to find a Tutor by their associated User
    Optional<Tutor> findByUser(User user);
}
