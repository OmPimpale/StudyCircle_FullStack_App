package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Session;
import com.studycircle.studycircle.model.SessionStatus;
import com.studycircle.studycircle.model.Subject;
import com.studycircle.studycircle.model.Tutor;
import com.studycircle.studycircle.model.Resource; // Import Resource
import com.studycircle.studycircle.repository.SessionRepository;
import com.studycircle.studycircle.repository.SubjectRepository;
import com.studycircle.studycircle.repository.TutorRepository;
import com.studycircle.studycircle.repository.ResourceRepository; // Import ResourceRepository
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.time.LocalDate; // Import LocalDate
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal; // Import BigDecimal

import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional; // Import Transactional


@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final TutorRepository tutorRepository;
    private final SubjectRepository subjectRepository;
    private final ResourceRepository resourceRepository; // Inject ResourceRepository


    @Autowired
    public SessionService(SessionRepository sessionRepository,
                          TutorRepository tutorRepository,
                          SubjectRepository subjectRepository,
                          ResourceRepository resourceRepository) { // Include ResourceRepository in constructor
        this.sessionRepository = sessionRepository;
        this.tutorRepository = tutorRepository;
        this.subjectRepository = subjectRepository;
        this.resourceRepository = resourceRepository; // Assign ResourceRepository
    }

    @Transactional // Add Transactional annotation
    public Session createSession(Long tutorId, Long subjectId, LocalDateTime startTime, LocalDateTime endTime, BigDecimal hourlyRate) { // Added hourlyRate
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start time and end time cannot be null");
        }
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        if (hourlyRate == null || hourlyRate.compareTo(BigDecimal.ZERO) <= 0) { // Basic validation for hourly rate
             throw new IllegalArgumentException("Hourly rate must be a positive value");
        }


        checkOverlap(tutorId, startTime, endTime, null);

        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new EntityNotFoundException("Tutor not found with ID: " + tutorId));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with ID: " + subjectId));


        Session session = new Session();
        session.setTutor(tutor);
        if (subject.getName() != null) {
             session.setSubject(subject.getName());
        } else {
             session.setSubject("Unknown Subject"); // Handle null subject name
        }
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        session.setStatus(SessionStatus.SCHEDULED.name());
        session.setHourlyRate(hourlyRate); // Set hourly rate

        return sessionRepository.save(session);
    }

    public Page<Session> getAllSessions(Pageable pageable) {
        return sessionRepository.findAll(pageable);
    }

    public Page<Session> getAllSessionsForTutor(Long tutorId, Pageable pageable) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new EntityNotFoundException("Tutor not found with ID: " + tutorId));
        return sessionRepository.findByTutor(tutor, pageable);
    }

    public Page<Session> getAllSessionsForStudent(Long studentId, Pageable pageable) {
        // Assuming Session has a ManyToOne relationship with Student and SessionRepository has findByStudentId
        // You might need to adjust this based on your actual data model and repository methods
        return sessionRepository.findByStudentId(studentId, pageable);
    }

    @Transactional // Add Transactional annotation
    public Session updateSession(Long id, LocalDateTime startTime, LocalDateTime endTime, String status) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with ID: " + id));

        if (startTime != null && endTime != null) {
             if (startTime.isAfter(endTime)) {
                 throw new IllegalArgumentException("Start time must be before end time");
             }
             checkOverlap(session.getTutor().getId(), startTime, endTime, session.getId());
             session.setStartTime(startTime);
             session.setEndTime(endTime);
        } else if (startTime != null) {
             if (session.getEndTime() != null && startTime.isAfter(session.getEndTime())) {
                 throw new IllegalArgumentException("Updated start time must be before existing end time");
             }
             checkOverlap(session.getTutor().getId(), startTime, session.getEndTime(), session.getId());
             session.setStartTime(startTime);
        } else if (endTime != null) {
             if (session.getStartTime() != null && session.getStartTime().isAfter(endTime)) {
                 throw new IllegalArgumentException("Updated end time must be after existing start time");
             }
             checkOverlap(session.getTutor().getId(), session.getStartTime(), endTime, session.getId());
             session.setEndTime(endTime);
        }


        if (status != null && !status.isEmpty()) {
            try {
                SessionStatus sessionStatus = SessionStatus.valueOf(status.toUpperCase());
                session.setStatus(sessionStatus.name());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid session status: " + status);
            }
        }

        return sessionRepository.save(session);
    }

    @Transactional // Add Transactional annotation
    public boolean cancelSession(Long id) {
        Optional<Session> sessionOptional = sessionRepository.findById(id);
        if (sessionOptional.isPresent()) {
            Session session = sessionOptional.get();
            session.setStatus(SessionStatus.CANCELLED.name());
            sessionRepository.save(session);
            return true;
        }
        return false;
    }

    @Transactional // Add Transactional annotation
    public void deleteSession(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with ID: " + id));
        // You might add checks here, e.g., only allow deleting if not booked
        sessionRepository.delete(session);
    }

    public Session getSessionById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with ID: " + id));
    }

    @Transactional // Add Transactional annotation
    public Session completeSession(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with ID: " + id));

        if (!session.getStatus().equals(SessionStatus.SCHEDULED.name())) {
            throw new IllegalStateException("Only scheduled sessions can be completed.");
        }
        // You might want to add more logic here, e.g., check if the session time has passed

        session.setStatus(SessionStatus.COMPLETED.name());
        return sessionRepository.save(session);
    }

    private void checkOverlap(Long tutorId, LocalDateTime startTime, LocalDateTime endTime, Long currentSessionId) {
        List<Session> overlappingSessions = sessionRepository.findOverlappingSessions(tutorId, startTime, endTime, currentSessionId); // Pass currentSessionId

        if (!overlappingSessions.isEmpty()) {
            throw new IllegalStateException("Tutor has overlapping sessions during this time.");
        }
    }

    public Page<Session> searchSessions(String subject, LocalDate date, Long tutorId, LocalDateTime startTime, LocalDateTime endTime, String status, Pageable pageable) {
        Specification<Session> spec = Specification.where(null);

        if (subject != null && !subject.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("subject")), "%" + subject.toLowerCase() + "%"));
        }

        if (date != null) {
             LocalDateTime startOfDay = date.atStartOfDay();
             LocalDateTime endOfDay = date.plusDays(1).atStartOfDay().minusNanos(1);
             spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("startTime"), startOfDay, endOfDay));
        }

        if (tutorId != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("tutor").get("id"), tutorId));
        }

        if (startTime != null && endTime != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                 criteriaBuilder.and(
                     criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), startTime),
                     criteriaBuilder.lessThanOrEqualTo(root.get("endTime"), endTime)
                 )
            );
        } else if (startTime != null) {
             spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), startTime));
        } else if (endTime != null) {
             spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("endTime"), endTime));
        }


        if (status != null && !status.isEmpty()) {
            try {
                SessionStatus sessionStatus = SessionStatus.valueOf(status.toUpperCase());
                spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), sessionStatus.name()));
            } catch (IllegalArgumentException e) {
                 throw new IllegalArgumentException("Invalid session status: " + status);
            }
        }


        return sessionRepository.findAll(spec, pageable);
    }

    // Add the missing getResourcesBySessionId method
    public List<Resource> getResourcesBySessionId(Long sessionId) {
        // Assuming ResourceRepository has a findBySessionId method that returns a List
        // If your findBySessionId in ResourceRepository returns Page, you might need to
        // fetch all content from the page or add a List returning method to ResourceRepository.
        // Example using a List returning method:
         return resourceRepository.findBySessionId(sessionId);
    }

    // You will need to add this method to your SessionRepository:
    // List<Session> findOverlappingSessions(Long tutorId, LocalDateTime startTime, LocalDateTime endTime);

    // Example using Specifications (requires additional setup and is more advanced):
    // public Page<Session> searchSessions(SessionSpecification spec, Pageable pageable) {
    //     return sessionRepository.findAll(spec, pageable);
    // }
}
