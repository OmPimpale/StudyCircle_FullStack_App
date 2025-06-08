package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.model.User;
import com.studycircle.studycircle.service.UserService; // Import UserService
import com.studycircle.studycircle.model.Notification; // Import Notification
import com.studycircle.studycircle.model.Student; // Import Student
import com.studycircle.studycircle.model.Tutor; // Import Tutor
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.security.Principal; // Import Principal
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import jakarta.persistence.EntityNotFoundException; // Import EntityNotFoundException

import java.util.List;
import java.util.Optional; // Keep one Optional import
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping("/register") // Updated to match service method signature
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) { // Use RegisterRequest DTO
        System.out.println("Received registration request for username: " + registerRequest.getUsername());
        try {
            // Call registerNewUser method from UserService
            User registeredUser = userService.registerNewUser(registerRequest.getFullName(),
                    registerRequest.getUsername(), registerRequest.getPassword());
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Return error message in body
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); // Return error message in body
        } catch (Exception e) {
            // Log the error: logger.error("Error during user registration", e);
            return new ResponseEntity<>("Error during registration", HttpStatus.INTERNAL_SERVER_ERROR); // Generic error
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getLoggedInUser(Principal principal) {
        // Assuming userService.findByUsername returns Optional<User>
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found")); // Handle Optional

        // Ensure User model has getRole() and getId() methods
        if ("STUDENT".equals(user.getRole())) {
            // Assuming userService has getStudentProfile(Long userId) method that returns
            // Optional<Student>
            Optional<Student> studentProfileOptional = userService.getStudentProfile(user.getId());
            if (studentProfileOptional.isPresent()) {
                return new ResponseEntity<>(studentProfileOptional.get(), HttpStatus.OK);
            } else {
                // If student profile not found, return user details or NOT_FOUND for the
                // profile
                return new ResponseEntity<>(user, HttpStatus.OK); // Or HttpStatus.NOT_FOUND for the profile
            }
        } else if ("TUTOR".equals(user.getRole())) {
            // Assuming userService has getTutorProfile(Long userId) method that returns
            // Optional<Tutor>
            Optional<Tutor> tutorProfileOptional = userService.getTutorProfile(user.getId());
            if (tutorProfileOptional.isPresent()) {
                return new ResponseEntity<>(tutorProfileOptional.get(), HttpStatus.OK);
            } else {
                // If tutor profile not found, return user details or NOT_FOUND for the profile
                return new ResponseEntity<>(user, HttpStatus.OK); // Or HttpStatus.NOT_FOUND for the profile
            }
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            // Assuming userService.updateUser returns a User or throws
            // EntityNotFoundException
            User user = userService.updateUser(id, updatedUser);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (EntityNotFoundException ex) { // Catch EntityNotFoundException
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Or a custom error response body
        } catch (Exception ex) {
            // Log the error: logger.error("Error updating user", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Generic error
        }
    }

    @PostMapping("/student-profile")
    public ResponseEntity<Student> createOrUpdateStudentProfile(Principal principal,
            @RequestBody Student studentProfile) {
        try {
            // Assuming userService.findByUsername returns Optional<User>
            User user = userService.findByUsername(principal.getName())
                    .orElseThrow(() -> new EntityNotFoundException("User not found")); // Handle Optional
            // Ensure User model has getId() method
            Student updatedProfile = userService.createOrUpdateStudentProfile(user.getId(), studentProfile);
            return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Or a custom error response body
        } catch (Exception ex) {
            // Log the error: logger.error("Error creating/updating student profile", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Generic error
        }
    }

    @PostMapping("/tutor-profile")
    public ResponseEntity<Tutor> createOrUpdateTutorProfile(Principal principal, @RequestBody Tutor tutorProfile) {
        try {
            // Assuming userService.findByUsername returns Optional<User>
            User user = userService.findByUsername(principal.getName())
                    .orElseThrow(() -> new EntityNotFoundException("User not found")); // Handle Optional
            // Ensure User model has getId() method
            Tutor updatedProfile = userService.createOrUpdateTutorProfile(user.getId(), tutorProfile);
            return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Or a custom error response body
        } catch (Exception ex) {
            // Log the error: logger.error("Error creating/updating tutor profile", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Generic error
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
        boolean success = userService.confirmPasswordReset(resetPasswordRequest.getToken(),
                resetPasswordRequest.getNewPassword());
        return success ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Or
                                                                                                             // another
                                                                                                             // suitable
                                                                                                             // status
    }

    @GetMapping("/{userId}/notifications")
    public ResponseEntity<Page<Notification>> getUserNotifications(@PathVariable Long userId,
            @PageableDefault(size = 10, sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {
        // Call getNotificationsByUserId method from UserService
        Page<Notification> notifications = userService.getNotificationsByUserId(userId, pageable);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PutMapping("/{userId}/notifications/{notificationId}/mark-as-read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long userId, @PathVariable Long notificationId,
            Principal principal) {
        try {
            // Call markNotificationAsRead method from UserService with 2 arguments
            userService.markNotificationAsRead(notificationId, userId); // Order might matter, check service method
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EntityNotFoundException ex) { // Catch EntityNotFoundException
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Notification not found
        } catch (Exception ex) {
            // Log the error: logger.error("Error marking notification as read", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Generic error
        }
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

    // Request body class for registration (matches UserService.registerNewUser)
    static class RegisterRequest {
        private String fullName;
        private String username; // Assuming username is the email
        private String password;

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
