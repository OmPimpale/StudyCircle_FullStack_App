package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Student;
import com.studycircle.studycircle.model.Tutor;
import com.studycircle.studycircle.model.Notification;
import com.studycircle.studycircle.model.User;
import com.studycircle.studycircle.repository.StudentRepository;
import com.studycircle.studycircle.repository.TutorRepository;
import com.studycircle.studycircle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final TutorRepository tutorRepository;
    private final EmailService emailService; // Inject EmailService
    private final NotificationRepository notificationRepository; // Inject NotificationRepository

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, StudentRepository studentRepository, TutorRepository tutorRepository, EmailService emailService, NotificationRepository notificationRepository) {
        this.studentRepository = studentRepository;
        this.tutorRepository = tutorRepository;
 this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // Basic method to find a user by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Method to find a user by email (needed for password reset)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Method to get user details by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User registerNewUser(String fullName, String username, String password) {
        // Basic validation
 if (username == null || username.isEmpty() || password == null || password.isEmpty() || fullName == null || fullName.isEmpty()) {
 throw new IllegalArgumentException("Full name, username (email), and password are required.");
 }

 // Validate email format (basic check, more robust validation can be added)
 if (!username.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
 throw new IllegalArgumentException("Invalid email format.");
 }

 // Check if email is already in use
 if (userRepository.findByEmail(username) != null) {
 throw new IllegalArgumentException("Email address is already registered.");
 }

        User newUser = new User();
        // Assuming username is the email
        newUser.setUsername(username);
        newUser.setEmail(username); // Set email field as well
 newUser.setPassword(passwordEncoder.encode(password));
 newUser.setRole("STUDENT"); // Default role is STUDENT

 try {
            String[] nameParts = fullName.split(" ", 2);
            newUser.setFirstName(nameParts.length > 0 ? nameParts[0] : "");
            newUser.setLastName(nameParts.length > 1 ? nameParts[1] : "");
 return userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
 // Handle cases where username or email might be duplicated at the database level
 throw new IllegalArgumentException("Email address is already registered.", e);
        } catch (Exception e) {
 throw new RuntimeException("Error during user registration.", e);
        }
    }

    // Method to initiate password reset
    public boolean initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            user.setResetPasswordToken(token);
            user.setResetPasswordExpiry(LocalDateTime.now().plusHours(24)); // Token expires in 24 hours
            userRepository.save(user);

 try {
                // Send email with reset link
                String resetLink = "YOUR_FRONTEND_RESET_PASSWORD_URL?token=" + token; // Replace with your frontend URL
                emailService.sendEmail(
 user.getEmail(),
 "Password Reset Request for StudyCircle",
 "To reset your password, click on the following link: " + resetLink + "\n\nThis link will expire in 24 hours."
                );
            } catch (MailException e) {
 // Log email sending failure, but don't prevent the token from being saved
 System.err.println("Failed to send password reset email: " + e.getMessage());
            }
            return true;
        }
        return false;
    }

    // Method to confirm password reset
    public boolean confirmPasswordReset(String token, String newPassword) {
        User user = userRepository.findByResetPasswordToken(token);
        if (user != null && user.getResetPasswordExpiry() != null && user.getResetPasswordExpiry().isAfter(LocalDateTime.now())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetPasswordToken(null);
            user.setResetPasswordExpiry(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Method to create or update student profile
    public Student createOrUpdateStudentProfile(Long userId, Student studentProfile) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
 if (studentProfile == null) {
 throw new IllegalArgumentException("Student profile data cannot be null.");
 }
        studentProfile.setUser(user);
 return studentRepository.save(studentProfile);
    }

    // Method to create or update tutor profile
    public Tutor createOrUpdateTutorProfile(Long userId, Tutor tutorProfile) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
 if (tutorProfile == null) {
 throw new IllegalArgumentException("Tutor profile data cannot be null.");
 }
        tutorProfile.setUser(user);
 return tutorRepository.save(tutorProfile);
    }

    // Method to update user details
 public User updateUser(Long id, User updatedUser) {
 Optional<User> existingUserOptional = userRepository.findById(id);

 if (existingUserOptional.isPresent()) {
 User existingUser = existingUserOptional.get();
            // Add validation for updatedUser fields if necessary
 // For example, validate email format if email is being updated

 return userRepository.save(existingUser);
        } else {
 throw new IllegalArgumentException("User not found with ID: " + id);
        }
    }

    // Method to get notifications for a user by user ID
 public Page<Notification> getNotificationsByUserId(Long userId, Pageable pageable) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
 return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        }
 return Page.empty(pageable); // Return an empty page if user not found
    }

    // Method to get all users with pagination
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
