package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Review;
import com.studycircle.studycircle.model.Session; // Import Session
import com.studycircle.studycircle.model.User; // Import User
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; // Import JpaSpecificationExecutor
import org.springframework.stereotype.Repository; // Import Repository


import java.time.LocalDateTime;


@Repository // Add Repository annotation
public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> { // Extend JpaSpecificationExecutor
    Page<Review> findAll(Pageable pageable);
    Page<Review> findBySessionId(Long sessionId, Pageable pageable);
    Page<Review> findByUserId(Long userId, Pageable pageable);
    boolean existsBySessionAndUser(com.studycircle.studycircle.model.Session session, com.studycircle.studycircle.model.User user);

    Page<Review> findByRating(Integer rating, Pageable pageable);

    Page<Review> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Add the methods used in ReviewService that take Session and User objects

    Page<Review> findBySession(Session session, Pageable pageable);

    Page<Review> findByUser(User user, Pageable pageable);

    // You might also need a method to check existence by review ID before deleting
    // boolean existsById(Long id); // Already available from JpaRepository

}
