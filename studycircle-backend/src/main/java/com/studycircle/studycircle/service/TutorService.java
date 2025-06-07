package com.studycircle.studycircle.service;

import com.studycircle.studycircle.dto.AvailableTimeSlot; // Import AvailableTimeSlot
import com.studycircle.studycircle.model.Tutor;
import com.studycircle.studycircle.model.Session; // Import Session
import com.studycircle.studycircle.repository.TutorRepository;
import com.studycircle.studycircle.repository.SessionRepository; // Import SessionRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException; // Import EntityNotFoundException
import org.springframework.transaction.annotation.Transactional; // Import Transactional

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collections; // Import Collections

@Service
public class TutorService {

    private final TutorRepository tutorRepository;
    private final SessionRepository sessionRepository; // Inject SessionRepository

    @Autowired
    public TutorService(TutorRepository tutorRepository, SessionRepository sessionRepository) {
        this.tutorRepository = tutorRepository;
        this.sessionRepository = sessionRepository;
    }

    // Add methods for tutor-related business logic here
    public List<Tutor> findAllTutors() {
        return tutorRepository.findAll();
    }

    public Optional<Tutor> findTutorById(Long id) {
        return tutorRepository.findById(id);
    }

    @Transactional // Add Transactional annotation
    public Tutor updateTutor(Long id, Tutor updatedTutor) {
        Optional<Tutor> existingTutorOptional = tutorRepository.findById(id);
        if (existingTutorOptional.isPresent()) {
            Tutor existingTutor = existingTutorOptional.get();

            // Update relevant fields
            // Ensure Tutor model has getQualifications and setQualifications methods
            if (updatedTutor.getQualifications() != null && !updatedTutor.getQualifications().isEmpty()) {
                existingTutor.setQualifications(updatedTutor.getQualifications());
            }
            // Ensure Tutor model has getExperience and setExperience methods
            if (updatedTutor.getExperience() != null && !updatedTutor.getExperience().isEmpty()) {
                existingTutor.setExperience(updatedTutor.getExperience());
            }
            // Ensure Tutor model has getBio and setBio methods
            if (updatedTutor.getBio() != null && !updatedTutor.getBio().isEmpty()) {
                existingTutor.setBio(updatedTutor.getBio());
            }
            // Corrected reference to updatedTutor.getProfilePictureUrl() and updatedTutor.getUser().getProfilePictureUrl()
            // Assuming profile picture URL is directly on the Tutor or accessible via Tutor.getUser().getProfilePictureUrl()
            if (updatedTutor.getProfilePictureUrl() != null && !updatedTutor.getProfilePictureUrl().isEmpty()) {
                 existingTutor.setProfilePictureUrl(updatedTutor.getProfilePictureUrl());
            } else if (updatedTutor.getUser() != null && updatedTutor.getUser().getProfilePictureUrl() != null && !updatedTutor.getUser().getProfilePictureUrl().isEmpty()) {
                 existingTutor.setProfilePictureUrl(updatedTutor.getUser().getProfilePictureUrl());
            }

            // Updating subjects might require a separate method or careful handling
            // Ensure Tutor model has getSubjects and setSubjects methods
            // if (updatedTutor.getSubjects() != null) {
            //     existingTutor.setSubjects(updatedTutor.getSubjects()); // This might need more complex logic
            // }
            // Update relationship with User if needed (consider carefully)
            // Ensure Tutor model has getUser and setUser methods
            // if (updatedTutor.getUser() != null) {
            //     existingTutor.setUser(updatedTutor.getUser());
            // }


            return tutorRepository.save(existingTutor);
        } else {
            throw new EntityNotFoundException("Tutor not found with ID: " + id); // Throw exception if not found
        }
    }

    public List<Tutor> findTutorsBySubject(String subject) {
        // Ensure TutorRepository has findBySubjectsName method
        return tutorRepository.findBySubjectsName(subject);
    }

    public List<AvailableTimeSlot> getAvailableTimeSlots(Long tutorId, LocalDate date) {
        // Ensure Tutor exists
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new EntityNotFoundException("Tutor not found with ID: " + tutorId));

        // Get the start and end time of the day
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay(); // End of the day is the start of the next day

        // Retrieve the tutor's scheduled sessions for the given date
        // Ensure SessionRepository has findByTutorIdAndStartTimeBetween method
        List<Session> scheduledSessions = sessionRepository.findByTutorIdAndStartTimeBetween(tutorId, startOfDay, endOfDay);

        // TODO: Implement logic to retrieve the tutor's general availability for the given date
        // This would involve fetching availability data from your Tutor model or a separate entity.
        // For now, let's assume the tutor is generally available all day for demonstration purposes.
        List<AvailableTimeSlot> generalAvailability = new ArrayList<>();
        // Assuming full day availability from 9 AM to 5 PM as a simple example
        LocalDateTime availabilityStart = date.atTime(LocalTime.of(9, 0));
        LocalDateTime availabilityEnd = date.atTime(LocalTime.of(17, 0));

        // For simplicity, let's add one large availability slot for the day if no sessions are scheduled
        if (scheduledSessions.isEmpty()) {
             if (availabilityStart.isBefore(availabilityEnd)) {
                  generalAvailability.add(new AvailableTimeSlot(availabilityStart, availabilityEnd));
             }
        } else {
             // TODO: Implement logic to subtract scheduled sessions from general availability
             // This is a more complex task and requires careful handling of time intervals.
             // You would iterate through the general availability periods and remove the scheduled session times.
             // For a basic example, let's just return no available slots if there are scheduled sessions.
             return Collections.emptyList(); // Return empty list if there are scheduled sessions (simplified)
        }


        // TODO: Generate available time slots by comparing general availability and scheduled sessions
        // This involves iterating through the general availability time ranges and subtracting the scheduled session times.
        // You'll need to define what a "time slot" is (e.g., 30 minutes, 1 hour) and generate slots accordingly.
        // This is a complex algorithm and depends on how you define and store general availability.

        // For a complete implementation, you would need to iterate through the `generalAvailability` and `scheduledSessions`
        // to calculate the free time slots.

        // Return a list of AvailableTimeSlot DTOs
        // Placeholder return for now, replace with actual calculated available slots
        return generalAvailability;
    }

    // You will likely need these methods in your TutorRepository:
    // List<Tutor> findBySubjectsName(String subjectName);

    // You will also need to ensure your Tutor model has:
    // - Getter and setter methods for qualifications, experience, bio, and profilePictureUrl
    // - A getUser() method if profilePictureUrl is accessed via the User model
    // - Getter and setter methods for subjects if they are stored in the Tutor model
}
