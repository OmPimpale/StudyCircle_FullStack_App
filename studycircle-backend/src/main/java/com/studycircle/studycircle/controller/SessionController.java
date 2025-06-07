package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.model.Session;
import com.studycircle.studycircle.model.Resource; // Import Resource
import com.studycircle.studycircle.service.SessionService; // Import SessionService
import jakarta.persistence.EntityNotFoundException; // Import EntityNotFoundException
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalDate; // Import LocalDate
import java.util.List;
// Remove unused import: import java.util.Optional;
import java.math.BigDecimal; // Import BigDecimal

@RestController
@RequestMapping("/api/sessions")
@PreAuthorize("isAuthenticated()") // Secure all endpoints in this controller
public class SessionController {

    private final SessionService sessionService; // Use final

    @Autowired // Autowire constructor
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping // Updated to match service method signature
    public ResponseEntity<Session> createSession(
            @RequestParam Long tutorId,
            @RequestParam Long subjectId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam BigDecimal hourlyRate) { // Include hourlyRate
        try {
            // Call createSession method with individual parameters
            Session createdSession = sessionService.createSession(tutorId, subjectId, startTime, endTime, hourlyRate);
            return new ResponseEntity<>(createdSession, HttpStatus.CREATED); // Return 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Or a custom error response body
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
             // Log the error: logger.error("Error creating session", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Generic error
        }
    }

    // Endpoint to get all sessions with pagination
    @GetMapping
    public ResponseEntity<Page<Session>> getAllSessions(Pageable pageable) {
        Page<Session> sessions = sessionService.getAllSessions(pageable);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Session> getSessionById(@PathVariable Long id) {
        try {
            // Assuming getSessionById in service returns Session or throws EntityNotFoundException
            Session session = sessionService.getSessionById(id);
            return ResponseEntity.ok(session);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
             // Log the error: logger.error("Error getting session by ID", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Generic error
        }
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<Page<Session>> getAllSessionsForTutor(@PathVariable Long tutorId, Pageable pageable) {
        try {
            // Assuming getAllSessionsForTutor in service returns Page or throws EntityNotFoundException
            Page<Session> sessions = sessionService.getAllSessionsForTutor(tutorId, pageable);
            return ResponseEntity.ok(sessions);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
             // Log the error: logger.error("Error getting sessions for tutor", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Generic error
        }
    }

    @GetMapping("/student/{studentId}") // Updated return type and parameters to match service
    public ResponseEntity<Page<Session>> getAllSessionsForStudent(@PathVariable Long studentId, Pageable pageable) {
        try {
            // Call getAllSessionsForStudent method with studentId and Pageable
            Page<Session> sessions = sessionService.getAllSessionsForStudent(studentId, pageable);
            return ResponseEntity.ok(sessions);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
             // Log the error: logger.error("Error getting sessions for student", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Generic error
        }
    }

    @PutMapping("/{id}") // Updated to use Request Body DTO for updates and consistent error returns
    public ResponseEntity<Session> updateSession(@PathVariable Long id, @RequestBody SessionUpdateDetails sessionDetails) { // Use DTO
        try {
            // Call updateSession method with individual parameters
            Session updatedSession = sessionService.updateSession(id, sessionDetails.getStartTime(), sessionDetails.getEndTime(), sessionDetails.getStatus());
            return new ResponseEntity<>(updatedSession, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Return null body with NOT_FOUND status
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Return null body with BAD_REQUEST status
        } catch (IllegalStateException ex) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT); // Return null body with CONFLICT status
        } catch (Exception ex) {
             // Log the error: logger.error("Error updating session", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Return null body with INTERNAL_SERVER_ERROR status
        }
    }

    @DeleteMapping("/{id}") // Changed to deleteSession to match service method
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        try {
            // Call deleteSession method from service
            sessionService.deleteSession(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
             // Log the error: logger.error("Error deleting session", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Generic error
        }
    }

    @GetMapping("/search") // Updated parameters to match service method
    public ResponseEntity<Page<Session>> searchSessions(
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, // Use LocalDate for date
            @RequestParam(required = false) Long tutorId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime, // Optional startTime
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime, // Optional endTime
            @RequestParam(required = false) String status,
            Pageable pageable) {
        // Call searchSessions with correct parameters
        Page<Session> sessions = sessionService.searchSessions(subject, date, tutorId, startTime, endTime, status, pageable);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{sessionId}/resources") // Endpoint path corrected (already done in previous turn)
    public ResponseEntity<List<Resource>> getResourcesBySessionId(@PathVariable Long sessionId) {
        // Call getResourcesBySessionId method (needs to be added to service)
        List<Resource> resources = sessionService.getResourcesBySessionId(sessionId); // This method is missing in the service
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    // Helper class for session update request body
    static class SessionUpdateDetails {
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String status;
        // You might add other updateable fields here

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class) // Add exception handler for IllegalStateException
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}