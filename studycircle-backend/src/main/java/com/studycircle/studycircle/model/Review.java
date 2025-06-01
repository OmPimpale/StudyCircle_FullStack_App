package com.studycircle.studycircle.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
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
   @JoinColumn(name = "session_id", nullable = false)
   private Session session;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "student_id", nullable = false)
   private Student student;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id", nullable = false) // User who wrote the review
   private User user;

   @Column(nullable = false)
   private Integer rating; // e.g., 1 to 5

   @Column(columnDefinition = "TEXT")
   private String comment;

   @Column(name = "review_date")
   private LocalDateTime reviewDate;
}
