package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Import Repository

import java.util.List;

@Repository // Add Repository annotation
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    // Add the method to find tutors by subject name
    List<Tutor> findBySubjectsName(String subjectName);

    // You might also need other custom query methods for tutors here
}
