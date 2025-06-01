package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findAll(Pageable pageable);

    Page<Booking> findByStudentId(Long studentId, Pageable pageable);

    Page<Booking> findByTutorId(Long tutorId, Pageable pageable);

    boolean existsBySessionId(Long sessionId);
}
