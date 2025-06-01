package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Booking;
import com.studycircle.studycircle.model.Payment;
import com.studycircle.studycircle.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final NotificationService notificationService; // Corrected dependency name

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository, NotificationService notificationService) { // Corrected dependency name
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.notificationService = notificationService; // Corrected dependency name
    }

    @Transactional
    public Payment processPayment(Long bookingId, Object paymentDetails) { // Use Object or a specific class for payment details
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);

        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();

            // --- Payment Gateway Integration Placeholder ---
            // This is where you would interact with your chosen payment gateway (e.g., Stripe, PayPal).
            // You would use the 'paymentDetails' object to pass necessary information (e.g., card details, token).
            // The payment gateway would process the transaction and return a result (success/failure) and a transaction ID.
            // Replace this placeholder with your actual payment gateway integration code.
            String transactionId = "mock_transaction_id_" + System.currentTimeMillis(); // Replace with actual transaction ID from gateway
            double amount = booking.getSession().getHourlyRate(); // Example: get amount from session
            boolean paymentSuccessful = true; // Replace with actual result from gateway

            if (paymentSuccessful) {
                // Create and save the payment record
            Payment payment = new Payment();
            payment.setAmount(amount);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setTransactionId(transactionId);
            payment.setBooking(booking);

            // Update booking status to completed
            booking.setPaymentStatus("COMPLETED");
            bookingRepository.save(booking);

            // Trigger payment notification (assuming emailService exists)
            emailService.sendPaymentNotification(booking.getStudent().getUser().getEmail(), booking.getTutor().getUser().getEmail(), amount, booking.getSession().getSubject());
                // Retrieve student and tutor emails and session details
                String studentEmail = booking.getStudent().getUser().getEmail();
                String tutorEmail = booking.getTutor().getUser().getEmail();
                String studentName = booking.getStudent().getUser().getFirstName() + " " + booking.getStudent().getUser().getLastName();
                String tutorName = booking.getTutor().getUser().getFirstName() + " " + booking.getTutor().getUser().getLastName();
                String subject = booking.getSession().getSubject();
                LocalDateTime sessionStartTime = booking.getSession().getStartTime();
                // ... get other relevant session details ...

                // Send email to student
                String studentEmailSubject = "Payment Confirmation for Your Study Session";
                String studentEmailBody = "Dear " + booking.getStudent().getUser().getFirstName() + ",\n\n" +
                        "Your payment of $" + amount + " for the " + subject + " session has been successfully processed.\n\n" +
                        "Session Details:\n" +
                        "Subject: " + subject + "\n" +
                        "Start Time: " + sessionStartTime + "\n" +
                        // ... include other session details ...
                        "\nThank you for using StudyCircle!";
                notificationService.sendEmail(studentEmail, studentEmailSubject, studentEmailBody);

                // Send email to tutor
                String tutorEmailSubject = "New Booking and Payment Received";
                String tutorEmailBody = "Dear " + booking.getTutor().getUser().getFirstName() + ",\n\n" +
                        "You have received a new booking and payment of $" + amount + " for a " + subject + " session.\n\n" +
                        "Student Name: " + studentName + "\n" +
                        "Session Details:\n" +
                        "Subject: " + subject + "\n" +
                        "Start Time: " + sessionStartTime + "\n" +
                        // ... include other session details ...
                        "\nPlease check your dashboard for more details.";
                notificationService.sendEmail(tutorEmail, tutorEmailSubject, tutorEmailBody);

                return paymentRepository.save(payment); // Save the payment entity after successful processing
            } else {
                // Handle payment failure
                // You might want to update booking status to "PAYMENT_FAILED" or similar
                throw new RuntimeException("Payment failed for booking ID: " + bookingId);
            }
        } else {
            throw new RuntimeException("Booking not found with ID: " + bookingId);
        }
    }
}