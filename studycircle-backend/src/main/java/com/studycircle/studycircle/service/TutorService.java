package com.studycircle.studycircle.service;

import com.studycircle.studycircle.dto.AvailableTimeSlot;
import com.studycircle.studycircle.model.Tutor;
import com.studycircle.studycircle.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
@Service
public class TutorService {

    private final TutorRepository tutorRepository;

    @Autowired
    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    // Add methods for tutor-related business logic here
    public List<Tutor> findAllTutors() {
        return tutorRepository.findAll();
    }

    public Optional<Tutor> findTutorById(Long id) {
        return tutorRepository.findById(id);
    }

    public Tutor updateTutor(Long id, Tutor updatedTutor) {
        Optional<Tutor> existingTutorOptional = tutorRepository.findById(id);
        if (existingTutorOptional.isPresent()) {
            Tutor existingTutor = existingTutorOptional.get();

            // Update relevant fields
            if (updatedTutor.getQualifications() != null && !updatedTutor.getQualifications().isEmpty()) {
                existingTutor.setQualifications(updatedTutor.getQualifications());
            }
            if (updatedTutor.getExperience() != null && !updatedTutor.getExperience().isEmpty()) {
                existingTutor.setExperience(updatedTutor.getExperience());
            }
            if (updatedTutor.getBio() != null && !updatedTutor.getBio().isEmpty()) {
                existingTutor.setBio(updatedTutor.getBio());
            }
            if (updatedTutor.getProfilePictureUrl() != null && !updatedUser.getProfilePictureUrl().isEmpty()) {
                existingTutor.setProfilePictureUrl(updatedUser.getProfilePictureUrl());
            }
            // Updating subjects might require a separate method or careful handling
            // if (updatedTutor.getSubjects() != null) {
            //     existingTutor.setSubjects(updatedTutor.getSubjects()); // This might need more complex logic
            // }
            // Update relationship with User if needed (consider carefully)
            // if (updatedTutor.getUser() != null) {
            //     existingTutor.setUser(updatedTutor.getUser());
            // }


            return tutorRepository.save(existingTutor);
        }
        return null; // Or throw an exception
    }

    public List<Tutor> findTutorsBySubject(String subject) {
 return tutorRepository.findBySubjectsName(subject);
    }

    public List<AvailableTimeSlot> getAvailableTimeSlots(Long tutorId, LocalDate date) {
        // TODO: Implement logic to retrieve the tutor's scheduled sessions for the given date
        // You'll need to inject the SessionRepository and query for sessions by tutorId and date range
        // For example: List<Session> scheduledSessions = sessionRepository.findByTutorIdAndStartTimeBetween(tutorId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());

        // TODO: Implement logic to retrieve the tutor's general availability for the given date (e.g., from a field in Tutor or a separate entity)
        // Tutor tutor = tutorRepository.findById(tutorId).orElseThrow(() -> new EntityNotFoundException("Tutor not found"));
        // GeneralAvailability generalAvailability = tutor.getGeneralAvailabilityForDate(date);

        // TODO: Calculate the available time slots by comparing general availability and scheduled sessions
        // This will involve iterating through the general availability time ranges and subtracting the scheduled session times
        // You'll need to define what a "time slot" is (e.g., 30 minutes, 1 hour) and generate slots accordingly.

        // Return a list of AvailableTimeSlot DTOs
        return null; // Placeholder return
    }

}