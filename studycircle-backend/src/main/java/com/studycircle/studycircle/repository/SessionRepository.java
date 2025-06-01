package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Page<Session> findAll(Pageable pageable);

    Page<Session> findByTutorId(Long tutorId, Pageable pageable);
    Page<Session> findByStudentId(Long studentId, Pageable pageable);

    Page<Session> findByStartTimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Session> findByStatus(String status, Pageable pageable);

    Page<Session> findBySubjectContainingIgnoreCase(String subject, Pageable pageable);

 @Query("SELECT s FROM Session s WHERE s.tutor.id = :tutorId")
    Page<Session> findByTutorIdWithFilter(Long tutorId, Pageable pageable);

}