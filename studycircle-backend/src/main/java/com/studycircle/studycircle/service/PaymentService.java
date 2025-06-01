package com.studycircle.studycircle.service;

import com.studycircle.studycircle.dto.PaymentRequest;
import com.studycircle.studycircle.model.Booking;
import com.studycircle.studycircle.model.BookingStatus;
import com.studycircle.studycircle.model.Payment;
import com.studycircle.studycircle.repository.BookingRepository;
import com.studycircle.studycircle.repository.PaymentRepository;
import com.studycircle.studycircle.exception.PaymentProcessingException;
import com.studycircle.studycircle.exception.PaymentFailedException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studycircle.studycircle.exception.InvalidWebhookSignatureException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final NotificationService notificationService;

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
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);

        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();

            if (booking.getStatus() == BookingStatus.PAID || booking.getStatus() == BookingStatus.CANCELLED) {
                throw new IllegalStateException("Booking with ID " + bookingId + " is already paid or cancelled.");
            }

            String transactionId = null;
            BigDecimal amount = booking.getSession().getHourlyRate();

            try {
                PaymentRequest paymentRequest = (PaymentRequest) paymentDetails;

                // Simulate payment success and generate mock transaction ID
                boolean paymentSuccessful = true;
                if (paymentSuccessful) {
                    transactionId = "mock_transaction_id_" + System.currentTimeMillis();
                } else {
                    throw new PaymentFailedException("Payment processing failed via gateway.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new PaymentProcessingException("Error interacting with payment gateway", e);
            }

            if (transactionId != null) {
                Payment payment = new Payment();
                payment.setAmount(amount);
                payment.setPaymentDate(LocalDateTime.now());
                payment.setTransactionId(transactionId);
                payment.setBooking(booking);

                booking.setStatus(BookingStatus.PAID);
                bookingRepository.save(booking);

                // Notification details
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
                notificationService.sendEmail(studentEmail, studentEmailSubject, studentEmailBody);

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
                notificationService.sendEmail(tutorEmail, tutorEmailSubject, tutorEmailBody);

                return paymentRepository.save(payment);
            } else {
                booking.setStatus(BookingStatus.PAYMENT_FAILED);
                throw new RuntimeException("Payment failed for booking ID: " + bookingId);
            }
        } else {
            throw new RuntimeException("Booking not found with ID: " + bookingId);
        }
    }

    @Transactional
    public void handleWebhook(String payload, String signature) {
        // TODO: Implement webhook handling and verification logic here.
    }
}