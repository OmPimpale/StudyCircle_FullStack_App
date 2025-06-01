package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Booking;
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
import org.springframework.stereotype.Service;import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SessionRepository sessionRepository;
    private final StudentRepository studentRepository;
    private final TutorRepository tutorRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, SessionRepository sessionRepository, StudentRepository studentRepository, TutorRepository tutorRepository) {
        this.bookingRepository = bookingRepository;
 this.sessionRepository = sessionRepository;
 this.studentRepository = studentRepository;
 this.tutorRepository = tutorRepository;
    }

    // Add service methods here

    public Booking createNewBooking(Long sessionId, Long studentId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with ID: " + sessionId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));
 Tutor tutor = session.getTutor();
 if (tutor == null) {
 throw new IllegalStateException("Session does not have a tutor assigned.");
 }
 if (!"SCHEDULED".equals(session.getStatus())) {
 throw new IllegalStateException("Session is not available for booking. Current status: " + session.getStatus());
 }

        // TODO: Add logic to check if the session has already been booked

    public Booking updateBookingStatus(Long id, String newStatus) {
        Optional<Booking> existingBookingOptional = bookingRepository.findById(id);
        if (existingBookingOptional.isPresent()) {
            Booking existingBooking = existingBookingOptional.get();
            existingBooking.setStatus(newStatus);
            return bookingRepository.save(existingBooking);
        }
        return null; // Or throw an exception if booking not found
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public Page<Booking> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

 public Page<Booking> getAllBookingsForStudent(Long studentId, Pageable pageable) {
        // Assuming Booking entity has a ManyToOne relationship with Student
        // and a method like findByStudent_Id exists in BookingRepository
 return bookingRepository.findByStudentId(studentId, pageable);
    }

 public Page<Booking> getAllBookingsForTutor(Long tutorId, Pageable pageable) {
        // Assuming Booking entity has a ManyToOne relationship with Tutor
        // and a method like findByTutor_Id exists in BookingRepository
 return bookingRepository.findByTutorId(tutorId, pageable);
    }
}