package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.model.Session;
import com.studycircle.studycircle.model.Resource;
import com.studycircle.studycircle.service.SessionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/sessions")
@PreAuthorize("isAuthenticated()") // Secure all endpoints in this controller
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping
    public ResponseEntity<Session> createSession(@RequestBody Session session) {
        try {
            Session createdSession = sessionService.createSession(session);
            return ResponseEntity.ok(createdSession);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Or a more specific error response
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
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
            Session session = sessionService.getSessionById(id);
            return ResponseEntity.ok(session);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<Page<Session>> getAllSessionsForTutor(@PathVariable Long tutorId, Pageable pageable) {
        try {
            Page<Session> sessions = sessionService.getAllSessionsForTutor(tutorId, pageable);
            return ResponseEntity.ok(sessions);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Session>> getAllSessionsForStudent(@PathVariable Long studentId) {
       try {
            List<Session> sessions = sessionService.getAllSessionsForStudent(studentId); // Keep as List for now as per previous instruction, can update to Page later if needed.
           return ResponseEntity.ok(sessions);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable Long id, @RequestBody Session sessionDetails) {
        Session updatedSession = sessionService.updateSession(id, sessionDetails);
        if (updatedSession != null) {
            return ResponseEntity.ok(updatedSession);
        }
        return ResponseEntity.notFound().build(); // Should be caught by EntityNotFoundException
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelSession(@PathVariable Long id) {
        boolean cancelled = sessionService.cancelSession(id);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Session>> searchSessions(
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Long tutorId) {
        // Assuming SessionService has a searchSessions method
        List<Session> sessions = sessionService.searchSessions(subject, date, tutorId);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{sessionId}/resources")
    public ResponseEntity<List<Resource>> getResourcesBySessionId(@PathVariable Long sessionId) {
        List<Resource> resources = sessionService.getResourcesBySessionId(sessionId);
        return ResponseEntity.ok(resources);
    }
}