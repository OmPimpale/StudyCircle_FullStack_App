package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.model.Booking;
import com.studycircle.studycircle.service.BookingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.List;

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
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
 return new ResponseEntity<>(booking, HttpStatus.OK);
        }
 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

 @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
 return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

 @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
 return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) { // Corrected return type
        Booking createdBooking = bookingService.createBooking(booking); // Store the created booking
 return new ResponseEntity<>(bookingService.createBooking(booking), HttpStatus.CREATED);
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
 Page<Booking> bookings = bookingService.findBookingsByStudentId(studentId, pageable);
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
 Page<Booking> bookings = bookingService.findBookingsByTutorId(tutorId, pageable);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}