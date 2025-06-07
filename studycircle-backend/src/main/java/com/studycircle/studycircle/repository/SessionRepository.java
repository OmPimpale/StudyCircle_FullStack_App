package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Session;
import com.studycircle.studycircle.model.SessionStatus;
import com.studycircle.studycircle.model.Tutor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; // Import JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long>, JpaSpecificationExecutor<Session> { // Extend JpaSpecificationExecutor
    Page<Session> findAll(Pageable pageable);

    Page<Session> findByTutorId(Long tutorId, Pageable pageable);

    Page<Session> findByStudentId(Long studentId, Pageable pageable);

    Page<Session> findByStartTimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Session> findByStatus(String status, Pageable pageable); // Keep as String for status in DB

    Page<Session> findBySubjectContainingIgnoreCase(String subject, Pageable pageable);

    @Query("SELECT s FROM Session s WHERE s.tutor.id = :tutorId")
    Page<Session> findByTutorIdWithFilter(Long tutorId, Pageable pageable);

    Page<Session> findByTutor(Tutor tutor, Pageable pageable);

    @Query("SELECT s FROM Session s WHERE s.tutor.id = :tutorId AND s.startTime < :endTime AND s.endTime > :startTime" +
           " AND (:currentSessionId IS NULL OR s.id != :currentSessionId)") // Added currentSessionId exclusion
    List<Session> findOverlappingSessions(Long tutorId, LocalDateTime startTime, LocalDateTime endTime, Long currentSessionId);


    Page<Session> findBySubject(String subject, Pageable pageable);

    List<Session> findByTutorIdAndStartTimeBetween(Long tutorId, LocalDateTime start, LocalDateTime end); // For available time slots

}
