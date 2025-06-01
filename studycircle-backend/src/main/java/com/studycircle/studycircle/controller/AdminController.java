package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.model.User;
import com.studycircle.studycircle.model.Session;
import com.studycircle.studycircle.service.SessionService;
import com.studycircle.studycircle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/users")
    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable) {
        Page<User> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/sessions")
    public ResponseEntity<Page<Session>> getAllSessions(Pageable pageable) {
        Page<Session> sessions = sessionService.getAllSessions(pageable);
        return ResponseEntity.ok(sessions);
    }

    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reports/student-count")
    public ResponseEntity<Long> getStudentCount() {
        return ResponseEntity.ok(userService.countStudents());
    }

    @GetMapping("/reports/tutor-count")
    public ResponseEntity<Long> getTutorCount() {
        return ResponseEntity.ok(userService.countTutors());
    }
}