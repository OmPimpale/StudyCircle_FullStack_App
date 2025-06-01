package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            // Call the PaymentService to process the payment
            paymentService.processPayment(paymentRequest.getBookingId(), paymentRequest.getPaymentDetails());
            return ResponseEntity.ok("Payment processed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Payment failed: " + e.getMessage());
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> handlePaymentWebhook(@RequestBody String payload,
                                                  @RequestHeader(value = "X-Webhook-Signature", required = false) String signature) {
        try {
            // TODO: Implement proper webhook signature verification before processing the payload
            // Example: if (!paymentService.verifyWebhookSignature(payload, signature)) {
            //     return ResponseEntity.badRequest().body("Invalid webhook signature");
            // }
            paymentService.handleWebhook(payload, signature);
            return ResponseEntity.ok("Webhook received and processed");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error processing webhook: " + e.getMessage());
        }
    }
    // Request body class for payment processing
    static class PaymentRequest {
        private Long bookingId;
        private String paymentDetails; // Placeholder for payment details (e.g., token, card info)

        public Long getBookingId() { return bookingId; }
        public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
        public String getPaymentDetails() { return paymentDetails; }
        public void setPaymentDetails(String paymentDetails) { this.paymentDetails = paymentDetails; }
    }
}