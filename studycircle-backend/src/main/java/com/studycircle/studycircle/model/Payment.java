package com.studycircle.studycircle.model;

import jakarta.persistence.*;
// Remove Lombok annotations
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "payments")
// Remove Lombok annotations
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(nullable = false)
    private BigDecimal amount; // Changed to BigDecimal

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @OneToOne // Add OneToOne relationship with Booking
    @JoinColumn(name = "booking_id") // Assuming a booking_id column in payments table
    private Booking booking;


    // Manual Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    // You might need to add constructors if you removed @NoArgsConstructor and @AllArgsConstructor
    public Payment() {
    }

    public Payment(Long id, String transactionId, BigDecimal amount, LocalDateTime paymentDate, Booking booking) {
        this.id = id;
        this.transactionId = transactionId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.booking = booking;
    }
}
