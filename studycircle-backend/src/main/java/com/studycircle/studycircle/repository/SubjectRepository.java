package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}