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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
            return ResponseEntity.badRequest().body(null);
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
            List<Session> sessions = sessionService.getAllSessionsForStudent(studentId);
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
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelSession(@PathVariable Long id) {
        boolean cancelled = sessionService.cancelSession(id);
        if (cancelled) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Session>> searchSessions(
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) Long tutorId,
            @RequestParam(required = false) String status,
            Pageable pageable) {
        Page<Session> sessions = sessionService.searchSessions(subject, startDate, endDate, tutorId, status, pageable);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{sessionId}/resources")
    public ResponseEntity<List<Resource>> getResourcesBySessionId(@PathVariable Long sessionId) {
        List<Resource> resources = sessionService.getResourcesBySessionId(sessionId);
        return ResponseEntity.ok(resources);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}