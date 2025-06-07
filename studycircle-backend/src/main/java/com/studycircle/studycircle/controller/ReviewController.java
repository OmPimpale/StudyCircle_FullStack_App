package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.model.Review;
import com.studycircle.studycircle.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException; // Import EntityNotFoundException

import java.time.LocalDateTime; // Import LocalDateTime
import java.time.format.DateTimeParseException; // Import DateTimeParseException
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Add API endpoints for reviews here
    @PostMapping
    public ResponseEntity<Review> addReview(@RequestParam Long sessionId,
                                            @RequestParam Long userId,
                                            @RequestParam int rating,
                                            @RequestParam String comment) {
        // Call createReview method from ReviewService with individual parameters
        Review createdReview = reviewService.createReview(sessionId, userId, rating, comment);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<Page<Review>> getReviewsForSession(@PathVariable Long sessionId, Pageable pageable) {
        // Call getReviewsBySession method from ReviewService
        Page<Review> reviews = reviewService.getReviewsBySession(sessionId, pageable);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Review>> getReviewsByUserId(@PathVariable Long userId, Pageable pageable) {
        // Call getReviewsByUser method from ReviewService
        Page<Review> reviews = reviewService.getReviewsByUser(userId, pageable);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id,
                                              @RequestParam int rating,
                                              @RequestParam String comment) {
        // Call updateReview method from ReviewService with individual parameters
        Review review = reviewService.updateReview(id, rating, comment);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Review>> searchReviews(
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Long sessionId,
            @RequestParam(required = false) Long userId,
            Pageable pageable) {

        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;

        try {
            if (startDate != null && !startDate.isEmpty()) {
                startDateTime = LocalDateTime.parse(startDate); // Assuming ISO 8601 format
            }
            if (endDate != null && !endDate.isEmpty()) {
                endDateTime = LocalDateTime.parse(endDate); // Assuming ISO 8601 format
            }
        } catch (DateTimeParseException e) {
            // Return null body with BAD_REQUEST status to be consistent with return type
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Page<Review> reviews = reviewService.searchReviews(rating, startDateTime, endDateTime, sessionId, userId, pageable);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
