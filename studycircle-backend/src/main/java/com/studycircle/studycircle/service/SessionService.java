package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Session;
import com.studycircle.studycircle.model.SessionStatus;
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
import java.time.LocalTime;
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
 session.setStatus(SessionStatus.SCHEDULED); // Initial status

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
    }

    public Page<Session> getAllSessionsForStudent(Long studentId, Pageable pageable) {
        // Assuming you have a way to find sessions by student ID in your SessionRepository
 return sessionRepository.findByStudentId(studentId, pageable);
    // For now, let's assume sessions are listed for tutors
    }

    public Session updateSession(Long id, LocalDateTime startTime, LocalDateTime endTime, String status) {
        Session session = sessionRepository.findById(id)
 .orElseThrow(() -> new IllegalArgumentException("Session not found with ID: " + id));

        if (startTime != null) {
 // If only start time is updated, end time must be provided or already exist
 if (endTime == null && session.getEndTime() != null && startTime.isAfter(session.getEndTime())) {
 throw new IllegalArgumentException("Updated start time must be before existing end time");
 }
 session.setStartTime(startTime);
        }

        if (endTime != null) {
 // If only end time is updated, start time must be provided or already exist
 if (startTime == null && session.getStartTime() != null && session.getStartTime().isAfter(endTime)) {
                session.setEndTime(endTime);
 }
            if (status != null && !status.isEmpty()) {
                session.setStatus(status);
            }
            return sessionRepository.save(session);
        } else {
 throw new IllegalArgumentException("Updated end time must be after updated start time");
        }

 // Re-check overlap after potential time updates
 checkOverlap(session.getTutor().getId(), session.getStartTime(), session.getEndTime(), session.getId());

        if (status != null && !status.isEmpty()) {
 try {
 session.setStatus(SessionStatus.valueOf(status.toUpperCase()));
 } catch (IllegalArgumentException e) {
 throw new IllegalArgumentException("Invalid session status: " + status);
 }
        }

 return sessionRepository.save(session);
    }

    public void cancelSession(Long id) {
        Session session = sessionRepository.findById(id)
 .orElseThrow(() -> new IllegalArgumentException("Session not found with ID: " + id));
        session.setStatus(SessionStatus.CANCELLED);
        sessionRepository.save(session);
    }

 public void deleteSession(Long id) {
        Session session = sessionRepository.findById(id)
 .orElseThrow(() -> new IllegalArgumentException("Session not found with ID: " + id));
 // You might add checks here, e.g., only allow deleting if not booked
 sessionRepository.delete(session);
 }

    public Session getSessionById(Long id) {
 return sessionRepository.findById(id)
 .orElseThrow(() -> new IllegalArgumentException("Session not found with ID: " + id));
    }

    public Session completeSession(Long id) {
        Session session = sessionRepository.findById(id)
 .orElseThrow(() -> new IllegalArgumentException("Session not found with ID: " + id));

 if (session.getStatus() != SessionStatus.SCHEDULED) {
 throw new IllegalStateException("Only scheduled sessions can be completed.");
 }
 // You might want to add more logic here, e.g., check if the session time has passed

        session.setStatus(SessionStatus.COMPLETED);
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

    public Page<Session> searchSessions(String subject, LocalDate date, Long tutorId, LocalDateTime startTime, LocalDateTime endTime, String status, Pageable pageable) {
        // Start with all sessions and apply filters based on provided parameters
        // This is a basic implementation. For more complex filtering, consider using Specifications.

        if (subject != null && date == null && tutorId == null && startTime == null && endTime == null && status == null) {
            return sessionRepository.findBySubject(subject, pageable);
        } else if (startTime != null && endTime != null && subject == null && date == null && tutorId == null && status == null) {
 return sessionRepository.findByStartTimeBetween(startTime, endTime, pageable);
        } else if (status != null && subject == null && date == null && tutorId == null && startTime == null && endTime == null) {
 try {
 return sessionRepository.findByStatus(SessionStatus.valueOf(status.toUpperCase()), pageable);
 } catch (IllegalArgumentException e) {
 throw new IllegalArgumentException("Invalid session status: " + status);
 }
        } else if (tutorId != null && subject == null && date == null && startTime == null && endTime == null && status == null) {
 return sessionRepository.findByTutorId(tutorId, pageable);
        }
        // Add more combinations of filters as needed.
        // For complex combinations, Spring Data JPA Specifications are recommended.

        // If no specific filters are provided, return all sessions with pagination
        return sessionRepository.findAll(pageable);
    }

    // You will need to add this method to your SessionRepository:
    // List<Session> findOverlappingSessions(Long tutorId, LocalDateTime startTime, LocalDateTime endTime);

    // Example using Specifications (requires additional setup and is more advanced):
    // public Page<Session> searchSessions(SessionSpecification spec, Pageable pageable) {
    //     return sessionRepository.findAll(spec, pageable);
    // }
}