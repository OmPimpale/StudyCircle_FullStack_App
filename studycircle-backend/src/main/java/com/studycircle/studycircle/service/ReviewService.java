package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Review;
import com.studycircle.studycircle.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // Add business logic methods here

    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> findReviewsByTutorId(Long tutorId) {
        return reviewRepository.findByTutorId(tutorId);
    }
}