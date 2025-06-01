package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Session;
import com.studycircle.studycircle.model.Subject;
import com.studycircle.studycircle.model.Tutor;
import com.studycircle.studycircle.repository.SessionRepository;
import com.studycircle.studycircle.repository.SubjectRepository;
import com.studycircle.studycircle.repository.TutorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private SubjectRepository subjectRepository; // Assuming you have a Subject entity and repository

    // Assuming Subject has a getName() method and Session has a setSubject() method that takes a String
    public Session createSession(Long tutorId, Long subjectId, LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
 throw new IllegalArgumentException("Start time and end time cannot be null");
        }
        if (startTime.isAfter(endTime)) {
 throw new IllegalArgumentException("Start time must be before end time");
        }

        checkOverlap(tutorId, startTime, endTime, null); // Check for overlap with existing sessions

        Optional<Tutor> tutorOptional = tutorRepository.findById(tutorId);
 if (!tutorOptional.isPresent()) {
 throw new IllegalArgumentException("Tutor not found with ID: " + tutorId);
        }
        Tutor tutor = tutorOptional.get();

        Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
 if (!subjectOptional.isPresent()) {
 throw new IllegalArgumentException("Subject not found with ID: " + subjectId);
        }
        Subject subject = subjectOptional.get();

        Session session = new Session();
        session.setTutor(tutor);
        session.setSubject(subject.getName());
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        session.setStatus("SCHEDULEED"); // Initial status

        return sessionRepository.save(session);
    }

    public Page<Session> getAllSessions(Pageable pageable) {
 return sessionRepository.findAll(pageable);
    }

 public Page<Session> getAllSessionsForTutor(Long tutorId, Pageable pageable) {
        Optional<Tutor> tutorOptional = tutorRepository.findById(tutorId);
        if (tutorOptional.isPresent()) {
 return sessionRepository.findByTutor(tutorOptional.get(), pageable);
        } else {
            // Handle case where tutor is not found
 throw new IllegalArgumentException("Tutor not found with ID: " + tutorId);
        }
    // For now, let's assume sessions are listed for tutors

    public Session updateSession(Long id, LocalDateTime startTime, LocalDateTime endTime, String status) {
        Session session = sessionRepository.findById(id)
 .orElseThrow(() -> new IllegalArgumentException("Session not found with ID: " + id));

        if (startTime != null) {
 if (endTime != null && startTime.isAfter(endTime)) {
 throw new IllegalArgumentException("Updated start time must be before updated end time");
            }
            session.setStartTime(startTime);
        }

        checkOverlap(session.getTutor().getId(), session.getStartTime(), session.getEndTime(), session.getId()); // Check overlap for update

        if (endTime != null) {
 if (startTime != null && startTime.isAfter(endTime)) {
                session.setEndTime(endTime);
            }
            if (status != null && !status.isEmpty()) {
                session.setStatus(status);
            }
            return sessionRepository.save(session);
        } else {
 throw new IllegalArgumentException("Updated end time must be after updated start time");
        }
    }

    public void cancelSession(Long id) {
        Session session = sessionRepository.findById(id)
 .orElseThrow(() -> new IllegalArgumentException("Session not found with ID: " + id));
        session.setStatus("CANCELLED");
        sessionRepository.save(session);
    }

    // Helper method to check for overlapping sessions
    private void checkOverlap(Long tutorId, LocalDateTime startTime, LocalDateTime endTime, Long currentSessionId) {
        List<Session> overlappingSessions = sessionRepository.findOverlappingSessions(tutorId, startTime, endTime);

        if (currentSessionId != null) {
            overlappingSessions.removeIf(session -> session.getId().equals(currentSessionId));
        }

        if (!overlappingSessions.isEmpty()) {
            throw new IllegalStateException("Tutor has overlapping sessions during this time.");
        }
    }

    // You will need to add this method to your SessionRepository:
    // List<Session> findOverlappingSessions(Long tutorId, LocalDateTime startTime, LocalDateTime endTime);
}