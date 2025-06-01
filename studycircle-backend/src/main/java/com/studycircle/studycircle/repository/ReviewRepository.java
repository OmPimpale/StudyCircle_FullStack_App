package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAll(Pageable pageable);
    Page<Review> findBySessionId(Long sessionId, Pageable pageable);
    Page<Review> findByUserId(Long userId, Pageable pageable);
    boolean existsBySessionAndUser(com.studycircle.studycircle.model.Session session, com.studycircle.studycircle.model.User user);
}