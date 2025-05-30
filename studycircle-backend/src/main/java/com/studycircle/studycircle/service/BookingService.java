package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Booking;
import com.studycircle.studycircle.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Add service methods here

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

}