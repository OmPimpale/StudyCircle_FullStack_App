package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.model.Booking;
import com.studycircle.studycircle.service.BookingService; // Import BookingService
import jakarta.persistence.EntityNotFoundException; // Import EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Remove duplicate import: import java.util.ArrayList;
// Remove unused imports: import java.util.Arrays;
import java.util.List; // Keep one List import
import java.util.Optional; // Import Optional

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<Page<Booking>> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        Sort sortOrder = Sort.by(Sort.Direction.ASC, sort); // Default to ascending sort
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Booking> bookings = bookingService.getAllBookings(pageable);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        // Handle Optional return type from service
        Optional<Booking> booking = bookingService.getBookingById(id);
        return booking.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @PostMapping // Updated to match service method signature and consistent error returns
    public ResponseEntity<Booking> createBooking(@RequestParam Long sessionId, @RequestParam Long studentId) {
        try {
            // Call createNewBooking method from BookingService
            Booking createdBooking = bookingService.createNewBooking(sessionId, studentId);
            return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Return null body with NOT_FOUND status
        } catch (IllegalStateException ex) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT); // Return null body with CONFLICT status
        } catch (Exception ex) {
             // Log the error: logger.error("Error creating booking", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Return null body with INTERNAL_SERVER_ERROR status
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<Page<Booking>> getBookingsByStudentId(
            @PathVariable Long studentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        Sort sortOrder = Sort.by(Sort.Direction.ASC, sort); // Default to ascending sort
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        // Call getAllBookingsForStudent method from BookingService
        Page<Booking> bookings = bookingService.getAllBookingsForStudent(studentId, pageable);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<Page<Booking>> getBookingsByTutorId(
            @PathVariable Long tutorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        Sort sortOrder = Sort.by(Sort.Direction.ASC, sort); // Default to ascending sort
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        // Call getAllBookingsForTutor method from BookingService
        Page<Booking> bookings = bookingService.getAllBookingsForTutor(tutorId, pageable);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // You might want to add endpoints for updating and deleting bookings based on your service methods
     @PutMapping("/{id}/status") // Consistent error returns
     public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long id, @RequestParam String status) {
         try {
             Booking updatedBooking = bookingService.updateBookingStatus(id, status);
             if (updatedBooking != null) {
                 return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
             } else {
                 return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Booking not found, return null body with NOT_FOUND status
             }
         } catch (IllegalArgumentException ex) {
             return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Invalid status, return null body with BAD_REQUEST status
         } catch (Exception ex) {
              // Log the error: logger.error("Error updating booking status", ex);
             return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Catch other potential errors, return null body with INTERNAL_SERVER_ERROR status
         }
     }

    // Assuming you have a deleteBooking method in your service
     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
         try {
             // Assuming deleteBooking in service handles EntityNotFoundException
             bookingService.deleteBooking(id);
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         } catch (EntityNotFoundException ex) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Booking not found
         } catch (Exception ex) {
              // Log the error: logger.error("Error deleting booking", ex);
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Catch other potential errors
         }
     }
}
