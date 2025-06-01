package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.model.User;
import com.studycircle.studycircle.service.UserService;
import com.studycircle.studycircle.model.Notification;
import com.studycircle.studycircle.model.Student;
import com.studycircle.studycircle.model.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;import java.util.Optional;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Add controller methods here

 @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<User> users = userService.getAllUsers(pageable);
 return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
 return ResponseEntity.badRequest().body(null); // Or a custom error response body
        } catch (IllegalStateException e) {
 return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Or a custom error response body
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getLoggedInUser(Principal principal) {
        Optional<User> optionalUser = userService.findByUsername(principal.getName());
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();
        if ("STUDENT".equals(user.getRole())) {
            return new ResponseEntity<>(userService.getStudentProfile(user.getId()), HttpStatus.OK);
        } else if ("TUTOR".equals(user.getRole())) {
            return new ResponseEntity<>(userService.getTutorProfile(user.getId()), HttpStatus.OK);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            User user = userService.updateUser(id, updatedUser);
            if (user != null) {
 return new ResponseEntity<>(user, HttpStatus.OK);
            }
 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
 return ResponseEntity.badRequest().body(null); // Or a custom error response body
        }
    }

    @PostMapping("/student-profile")
    public ResponseEntity<Student> createOrUpdateStudentProfile(Principal principal, @RequestBody Student studentProfile) {
        try {
 Optional<User> optionalUser = userService.findByUsername(principal.getName());
            if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
 User user = optionalUser.get();
 Student updatedProfile = userService.createOrUpdateStudentProfile(user.getId(), studentProfile);
 return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
 return ResponseEntity.badRequest().body(null); // Or a custom error response body
        }
    }

    @PostMapping("/tutor-profile")
    public ResponseEntity<Tutor> createOrUpdateTutorProfile(Principal principal, @RequestBody Tutor tutorProfile) {
        try {
 Optional<User> optionalUser = userService.findByUsername(principal.getName());
            if (!optionalUser.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
 Tutor updatedProfile = userService.createOrUpdateTutorProfile(optionalUser.get().getId(), tutorProfile);
 return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
 return ResponseEntity.badRequest().body(null); // Or a custom error response body
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> initiatePasswordReset(@RequestBody String email) {
        boolean success = userService.initiatePasswordReset(email);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Or another suitable status
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> confirmPasswordReset(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        boolean success = userService.confirmPasswordReset(resetPasswordRequest.getToken(), resetPasswordRequest.getNewPassword());
        return success ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Or another suitable status
    }

    @GetMapping("/{userId}/notifications")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable Long userId) {
        List<Notification> notifications = userService.getUserNotifications(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    // Helper class for password reset request body
    static class ResetPasswordRequest {
        private String token;
        private String newPassword;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }

}