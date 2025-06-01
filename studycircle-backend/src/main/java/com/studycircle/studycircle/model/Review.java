package com.studycircle.studycircle.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private Integer rating; // e.g., 1 to 5

    @Column(columnDefinition = "TEXT")
    private String comment;

    @CreationTimestamp
 @Column(name = "review_timestamp")
 private LocalDateTime reviewTimestamp;

    @Column(name = "is_public")
    private boolean isPublic;
}