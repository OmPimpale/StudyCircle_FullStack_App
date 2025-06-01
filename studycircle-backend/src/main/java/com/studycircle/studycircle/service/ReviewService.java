package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Session;
import com.studycircle.studycircle.model.User;
import com.studycircle.studycircle.model.Review;
import com.studycircle.studycircle.repository.ReviewRepository;
import com.studycircle.studycircle.repository.SessionRepository;
import com.studycircle.studycircle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studycircle.studycircle.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;



import java.util.List;
import java.time.LocalDateTime;



@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, SessionRepository sessionRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

 public Review createReview(Long sessionId, Long userId, int rating, String comment) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + sessionId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Validate rating range
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        // Check if user has already reviewed this session
        if (reviewRepository.existsBySessionAndUser(session, user)) {
            throw new IllegalStateException("User has already reviewed this session.");
        }

        Review review = new Review();
        review.setSession(session);
        review.setUser(user);
        review.setRating(rating);
        review.setComment(comment);
        review.setReviewDate(review.getReviewDate() == null ? LocalDateTime.now() : review.getReviewDate());

        return reviewRepository.save(review);
    }

    public Page<Review> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    public Page<Review> getReviewsBySession(Long sessionId, Pageable pageable) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + sessionId));
        return reviewRepository.findBySession(session, pageable);
    }

    public Page<Review> getReviewsByUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return reviewRepository.findByUser(user, pageable);
    }

    public Review updateReview(Long reviewId, int rating, String comment) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));
        review.setRating(rating);
        review.setComment(comment);
        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

 public Page<Review> searchReviews(Integer rating, LocalDateTime startDate, LocalDateTime endDate, Long sessionId, Long userId, Pageable pageable) {
 Specification<Review> spec = Specification.where(null);

 if (rating != null) {
 spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("rating"), rating));
 }

 if (startDate != null && endDate != null) {
 spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("reviewDate"), startDate, endDate));
 } else if (startDate != null) {
 spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("reviewDate"), startDate));
 } else if (endDate != null) {
 spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("reviewDate"), endDate));
 }

 if (sessionId != null) {
 spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("session").get("id"), sessionId));
 }

 if (userId != null) {
 spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId));
 }

 return reviewRepository.findAll(spec, pageable);
    }
}