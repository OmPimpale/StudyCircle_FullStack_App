package com.studycircle.studycircle.service;

import com.studycircle.studycircle.dto.PaymentRequest;
import com.studycircle.studycircle.model.Booking;
import com.studycircle.studycircle.model.BookingStatus;
import com.studycircle.studycircle.model.Payment;
import com.studycircle.studycircle.repository.BookingRepository;
import com.studycircle.studycircle.repository.PaymentRepository;
import com.studycircle.studycircle.exception.PaymentProcessingException;
import com.studycircle.studycircle.exception.PaymentFailedException;
import com.studycircle.studycircle.exception.InvalidWebhookSignatureException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException; // Import EntityNotFoundException


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final NotificationService notificationService; // Assuming NotificationService exists

    @Autowired
    public PaymentService(
            PaymentRepository paymentRepository,
            BookingRepository bookingRepository,
            NotificationService notificationService) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public Payment processPayment(Long bookingId, Object paymentDetails) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with ID: " + bookingId)); // Use EntityNotFoundException

        // Compare with BookingStatus enum values
        if (booking.getStatus().equals(BookingStatus.PAID.name()) || booking.getStatus().equals(BookingStatus.CANCELLED.name())) {
            throw new IllegalStateException("Booking with ID " + bookingId + " is already paid or cancelled.");
        }

        String transactionId = null;
        // Use the new getHourlyRate() method from Session
        BigDecimal amount = booking.getSession().getHourlyRate();

        try {
            // Assuming PaymentRequest is a valid DTO and paymentDetails can be cast to it
            // You might need more sophisticated handling based on the actual payment gateway response
            PaymentRequest paymentRequest = (PaymentRequest) paymentDetails;

            // Simulate payment success and generate mock transaction ID
            boolean paymentSuccessful = true; // Replace with actual payment gateway logic
            if (paymentSuccessful) {
                transactionId = "mock_transaction_id_" + System.currentTimeMillis();
            } else {
                throw new PaymentFailedException("Payment processing failed via gateway."); // Use imported exception
            }

        } catch (PaymentFailedException e) {
             // Handle payment failure specifically
             booking.setStatus(BookingStatus.PAYMENT_FAILED.name()); // Set status as String
             bookingRepository.save(booking);
             throw e; // Re-throw the exception
        } catch (Exception e) {
            e.printStackTrace();
            throw new PaymentProcessingException("Error interacting with payment gateway", e); // Use imported exception
        }

        if (transactionId != null) {
            Payment payment = new Payment();
            payment.setAmount(amount);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setTransactionId(transactionId);
            payment.setBooking(booking);

            booking.setStatus(BookingStatus.PAID.name()); // Set status as String
            bookingRepository.save(booking);

            // Notification details
            // Ensure User, Student, Tutor models have necessary getter methods (e.g., getEmail, getFirstName, getLastName)
            String studentEmail = booking.getStudent().getUser().getEmail();
            String tutorEmail = booking.getTutor().getUser().getEmail();
            String subject = booking.getSession().getSubject();
            LocalDateTime sessionStartTime = booking.getSession().getStartTime();

            // Email to student
            String studentEmailSubject = "Payment Confirmation for Your Study Session";
            String studentEmailBody = "Dear " + booking.getStudent().getUser().getFirstName() + ",\n\n" +
                    "Your payment of $" + amount + " for the " + subject
                    + " session has been successfully processed.\n\n" +
                    "Session Details:\n" +
                    "Subject: " + subject + "\n" +
                    "Start Time: " + sessionStartTime + "\n" +
                    "\nThank you for using StudyCircle!";
            notificationService.sendNotificationEmail(studentEmail, studentEmailSubject, studentEmailBody); // Use sendNotificationEmail method

            // Email to tutor
            String tutorEmailSubject = "New Booking and Payment Received";
            String tutorEmailBody = "Dear " + booking.getTutor().getUser().getFirstName() + ",\n\n" +
                    "You have received a new booking and payment of $" + amount + " for a " + subject
                    + " session.\n\n" +
                    "Student Name: " + booking.getStudent().getUser().getFirstName() + " "
                    + booking.getStudent().getUser().getLastName() + "\n" +
                    "Session Details:\n" +
                    "Subject: " + subject + "\n" +
                    "Start Time: " + sessionStartTime + "\n" +
                    "\nPlease check your dashboard for more details.";
            notificationService.sendNotificationEmail(tutorEmail, tutorEmailSubject, tutorEmailBody); // Use sendNotificationEmail method

            return paymentRepository.save(payment);
        } else {
             // This block will be reached if transactionId is null after the try-catch,
             // which might happen if the payment gateway interaction didn't throw an exception
             // but also didn't provide a transaction ID.
            booking.setStatus(BookingStatus.PAYMENT_FAILED.name()); // Set status as String
            bookingRepository.save(booking);
            throw new PaymentProcessingException("Payment processing did not return a transaction ID for booking ID: " + bookingId);
        }
    }

    @Transactional
    public void handleWebhook(String payload, String signature) {
        // TODO: Implement webhook handling and verification logic here.
        // This method will receive notifications from the payment gateway.
        // You will likely need to:
        // 1. Verify the signature to ensure the webhook request is legitimate (using InvalidWebhookSignatureException).
        // 2. Parse the payload to extract relevant information (e.g., payment status, booking ID).
        // 3. Update the booking status and payment record in your database based on the webhook data.
        // 4. Handle potential edge cases and errors (e.g., duplicate webhooks, failed updates).
        System.out.println("Received webhook. Payload: " + payload + ", Signature: " + signature);
        // Basic placeholder implementation
        // In a real application, this would involve complex logic
        throw new UnsupportedOperationException("Webhook handling not yet fully implemented.");
    }
}
