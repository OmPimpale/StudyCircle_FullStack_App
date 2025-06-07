package com.studycircle.studycircle.model;

import jakarta.persistence.*;
// Remove Lombok annotations if you are adding getters and setters manually
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
// Remove Lombok annotations
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String status; // e.g., "SCHEDULED", "COMPLETED", "CANCELLED"

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = true) // Assuming student_id can be null initially
    private Student student;

    @Column(nullable = false)
    private BigDecimal hourlyRate;

    // You might want to add a field for meeting link later
    // private String meetingLink;

    // Manual Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    // You might need to add constructors if you removed @NoArgsConstructor and @AllArgsConstructor
    public Session() {
    }

    public Session(Long id, LocalDateTime startTime, LocalDateTime endTime, String subject, String status, Tutor tutor, Student student, BigDecimal hourlyRate) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.status = status;
        this.tutor = tutor;
        this.student = student;
        this.hourlyRate = hourlyRate;
    }
}
