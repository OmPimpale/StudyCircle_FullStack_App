package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Booking;
import com.studycircle.studycircle.model.BookingStatus; // Import BookingStatus
import com.studycircle.studycircle.repository.BookingRepository;
import com.studycircle.studycircle.model.Session;
import com.studycircle.studycircle.model.Student;
import com.studycircle.studycircle.model.Tutor;
import com.studycircle.studycircle.repository.SessionRepository;
import com.studycircle.studycircle.repository.StudentRepository;
import com.studycircle.studycircle.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException; // Import EntityNotFoundException
import org.springframework.transaction.annotation.Transactional; // Import Transactional


import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SessionRepository sessionRepository;
    private final StudentRepository studentRepository;
    private final TutorRepository tutorRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
            SessionRepository sessionRepository,
            StudentRepository studentRepository,
            TutorRepository tutorRepository) {
        this.bookingRepository = bookingRepository;
        this.sessionRepository = sessionRepository;
        this.studentRepository = studentRepository;
        this.tutorRepository = tutorRepository;
    }

    @Transactional // Add Transactional annotation
    public Booking createNewBooking(Long sessionId, Long studentId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with ID: " + sessionId)); // Use EntityNotFoundException
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId)); // Use EntityNotFoundException

        // Ensure Session model has getTutor() method
        Tutor tutor = session.getTutor();

        if (tutor == null) {
            throw new IllegalStateException("Session does not have a tutor assigned.");
        }

        // Ensure Session model has getStatus() method and compare with enum name
        if (!session.getStatus().equals(com.studycircle.studycircle.model.SessionStatus.SCHEDULED.name())) {
            throw new IllegalStateException(
                    "Session is not available for booking. Current status: " + session.getStatus());
        }

        // Assuming BookingRepository has existsBySessionId method
        boolean alreadyBooked = bookingRepository.existsBySessionId(sessionId);
        if (alreadyBooked) {
            throw new IllegalStateException("Session has already been booked.");
        }

        Booking booking = new Booking();
        // Ensure Booking model has setSession, setStudent, setTutor, and setStatus methods
        booking.setSession(session);
        booking.setStudent(student);
        booking.setTutor(tutor);
        booking.setStatus(BookingStatus.BOOKED.name()); // Set status using enum name
        // Set other fields as needed (e.g., bookingDate)
         // booking.setBookingDate(LocalDateTime.now());


        return bookingRepository.save(booking);
    }

    @Transactional // Add Transactional annotation
    public Booking updateBookingStatus(Long id, String newStatus) {
        Optional<Booking> existingBookingOptional = bookingRepository.findById(id);
        if (existingBookingOptional.isPresent()) {
            Booking existingBooking = existingBookingOptional.get();
            // Ensure Booking model has setStatus method and validate newStatus
            try {
                BookingStatus bookingStatus = BookingStatus.valueOf(newStatus.toUpperCase());
                existingBooking.setStatus(bookingStatus.name()); // Set status using enum name
                return bookingRepository.save(existingBooking);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid booking status: " + newStatus);
            }
        }
        return null; // Or throw an exception if booking not found (consider EntityNotFoundException)
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public Page<Booking> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    public Page<Booking> getAllBookingsForStudent(Long studentId, Pageable pageable) {
        // Assuming Booking entity has a ManyToOne relationship with Student
        // and BookingRepository has findByStudentId method
        return bookingRepository.findByStudentId(studentId, pageable);
    }

    public Page<Booking> getAllBookingsForTutor(Long tutorId, Pageable pageable) {
        // Assuming Booking entity has a ManyToOne relationship with Tutor
        // and BookingRepository has findByTutorId method
        return bookingRepository.findByTutorId(tutorId, pageable);
    }

    // Add the missing deleteBooking method
    @Transactional // Add Transactional annotation
    public void deleteBooking(Long id) {
        // You might add checks here, e.g., only allow deleting if in a certain status
        // Or check if the booking exists before deleting (optional, deleteById handles non-existence without error)
        bookingRepository.deleteById(id);
    }


    // You will likely need these methods in your BookingRepository:
    // boolean existsBySessionId(Long sessionId);
    // Page<Booking> findByStudentId(Long studentId, Pageable pageable);
    // Page<Booking> findByTutorId(Long tutorId, Pageable pageable);
}
