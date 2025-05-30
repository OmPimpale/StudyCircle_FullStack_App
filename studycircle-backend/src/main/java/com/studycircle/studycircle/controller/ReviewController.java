package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.model.Review;
import com.studycircle.studycircle.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Review createdReview = reviewService.addReview(review);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<Review>> getReviewsByTutorId(@PathVariable Long tutorId) {
        List<Review> reviews = reviewService.findReviewsByTutorId(tutorId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

}