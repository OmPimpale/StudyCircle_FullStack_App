package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Page<Session> findAll(Pageable pageable);

    Page<Session> findByTutorId(Long tutorId, Pageable pageable);
    Page<Session> findByStudentId(Long studentId, Pageable pageable);
}