package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.model.Booking;
import com.studycircle.studycircle.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Add controller methods here for handling booking requests

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking createdBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<java.util.List<Booking>> getBookingsByStudentId(@PathVariable Long studentId) {
        java.util.List<Booking> bookings = bookingService.findBookingsByStudentId(studentId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<java.util.List<Booking>> getBookingsByTutorId(@PathVariable Long tutorId) {
        java.util.List<Booking> bookings = bookingService.findBookingsByTutorId(tutorId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}