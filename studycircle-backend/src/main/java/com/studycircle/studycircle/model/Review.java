package com.studycircle.studycircle.model;

import jakarta.persistence.*;
// Remove Lombok annotations
// import lombok.Getter;
// import lombok.Setter;
// import lombok.NoArgsConstructor;
// import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
// Remove Lombok annotations
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
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

   @Column(name = "created_at")
   private LocalDateTime createdAt;

   // Manual Getters and Setters (including the missing setters)

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Booking getBooking() {
      return booking;
   }

   public void setBooking(Booking booking) {
      this.booking = booking;
   }

   public Session getSession() {
      return session;
   }

   // Manually added setter for session
   public void setSession(Session session) {
      this.session = session;
   }

   public Student getStudent() {
      return student;
   }

   public void setStudent(Student student) {
      this.student = student;
   }

   public User getUser() {
      return user;
   }

   // Manually added setter for user
   public void setUser(User user) {
      this.user = user;
   }

   public Integer getRating() {
      return rating;
   }

   // Manually added setter for rating
   public void setRating(Integer rating) {
      this.rating = rating;
   }

   public String getComment() {
      return comment;
   }

   // Manually added setter for comment
   public void setComment(String comment) {
      this.comment = comment;
   }

   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   // Manually added setter for createdAt
   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   // Manual constructors (if you removed @NoArgsConstructor and
   // @AllArgsConstructor)
   public Review() {
   }

   public Review(Long id, Booking booking, Session session, Student student, User user, Integer rating, String comment,
         LocalDateTime createdAt) {
      this.id = id;
      this.booking = booking;
      this.session = session;
      this.student = student;
      this.user = user;
      this.rating = rating;
      this.comment = comment;
      this.createdAt = createdAt;
   }
}
