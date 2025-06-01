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
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review createdReview = reviewService.createNewReview(review);
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
 Page<Review> reviews = reviewService.getReviewsForSession(sessionId, pageable);
 return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

 @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Review>> getReviewsByUserId(@PathVariable Long userId, Pageable pageable) {
 Page<Review> reviews = reviewService.getReviewsByUserId(userId, pageable);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

 @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review updatedReview) {
 Review review = reviewService.updateReview(id, updatedReview);
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
            @RequestParam(required = false) String startDate, // Assuming date as String for simplicity, parse in service
            @RequestParam(required = false) String endDate, // Assuming date as String for simplicity, parse in service
            @RequestParam(required = false) Long sessionId,
            @RequestParam(required = false) Long userId,
            Pageable pageable) {
        // Pass null for LocalDateTime parsing in service for now.
        Page<Review> reviews = reviewService.searchReviews(rating, null, null, sessionId, userId, pageable);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}