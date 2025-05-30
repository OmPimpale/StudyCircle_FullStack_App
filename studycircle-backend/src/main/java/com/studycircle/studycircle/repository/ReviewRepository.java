package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTutorId(Long tutorId);
}