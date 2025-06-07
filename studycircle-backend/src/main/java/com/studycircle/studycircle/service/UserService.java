package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Student;
import com.studycircle.studycircle.model.Tutor;
import com.studycircle.studycircle.model.Notification;
import com.studycircle.studycircle.model.User; // Import User
import com.studycircle.studycircle.repository.NotificationRepository; // Import NotificationRepository
import com.studycircle.studycircle.repository.StudentRepository;
import com.studycircle.studycircle.repository.TutorRepository;
import com.studycircle.studycircle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.dao.DataIntegrityViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException; // Import EntityNotFoundException
import org.springframework.transaction.annotation.Transactional; // Import Transactional

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;


import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final TutorRepository tutorRepository;
    private final NotificationService notificationService; // Inject NotificationService
    private final NotificationRepository notificationRepository; // Corrected type

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       StudentRepository studentRepository, TutorRepository tutorRepository,
                       NotificationService notificationService, // Corrected type
                       NotificationRepository notificationRepository) { // Corrected type
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.tutorRepository = tutorRepository;
        this.notificationService = notificationService; // Corrected assignment
        this.notificationRepository = notificationRepository;
    }

    // Basic method to find a user by username
    // Ensure UserRepository has findByUsername(String username) method
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Method to find a user by email (needed for password reset)
    // Ensure UserRepository has findByEmail(String email) method
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Method to get user details by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional // Add Transactional annotation
    public User registerNewUser(String fullName, String username, String password) {
        logger.info("Attempting to register new user with username: {}", username);
        // Basic validation
        if (username == null || username.isEmpty() || password == null || password.isEmpty() || fullName == null
                || fullName.isEmpty()) {
            logger.warn("Registration failed: Missing required fields for username: {}", username);
            throw new IllegalArgumentException("Full name, username (email), and password are required.");
        }

        // Validate email format (basic check, more robust validation can be added)
        if (!username.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            logger.warn("Registration failed: Invalid email format for username: {}", username);
            throw new IllegalArgumentException("Invalid email format.");
        }

        // Check if email is already in use
        logger.info("Checking if email already exists for username: {}", username);
        // Ensure UserRepository has findByEmail(String email) method
        if (userRepository.findByEmail(username) != null) {
            logger.warn("Registration failed: Email address already registered for username: {}", username);
            throw new IllegalArgumentException("Email address is already registered.");
        }
        logger.info("Email address is available for username: {}", username);

        User newUser = new User();
        // Assuming username is the email
        // Ensure User model has setUsername, setEmail, setPassword, setRole, setFirstName, setLastName methods
        newUser.setUsername(username);
        newUser.setEmail(username); // Set email field as well
        logger.info("Encoding password for username: {}", username);
        newUser.setPassword(passwordEncoder.encode(password));
        logger.info("Password encoded for username: {}", username);
        newUser.setRole("STUDENT"); // Default role is STUDENT

        try {
            String[] nameParts = fullName.split(" ", 2);
            newUser.setFirstName(nameParts.length > 0 ? nameParts[0] : "");
            newUser.setLastName(nameParts.length > 1 ? nameParts[1] : "");
            logger.info("Saving new user to database for username: {}", username);
            return userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            // Handle cases where username or email might be duplicated at the database
            // level
            throw new IllegalArgumentException("Email address is already registered.", e);
        } catch (Exception e) {
            logger.error("Error during user registration for username: {}", username, e);
            throw new RuntimeException("Error during user registration.", e);
        }
    }

    // Method to initiate password reset
    @Transactional // Add Transactional annotation
    public boolean initiatePasswordReset(String email) {
        // Ensure UserRepository has findByEmail(String email) method
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            // Ensure User model has setResetPasswordToken and setResetPasswordExpiry methods
            user.setResetPasswordToken(token);
            user.setResetPasswordExpiry(LocalDateTime.now().plusHours(24)); // Token expires in 24 hours
            userRepository.save(user);

            try {
                // Send email with reset link
                String resetLink = "YOUR_FRONTEND_RESET_PASSWORD_URL?token=" + token; // Replace with your frontend URL
                // Ensure NotificationService has sendNotificationEmail method
                notificationService.sendNotificationEmail( // Corrected method call
                        user.getEmail(), // Ensure User model has getEmail() method
                        "Password Reset Request for StudyCircle",
                        "To reset your password, click on the following link: " + resetLink
                                + "\n\nThis link will expire in 24 hours.");
            } catch (MailException e) {
                // Log email sending failure, but don't prevent the token from being saved
                System.err.println("Failed to send password reset email: " + e.getMessage());
                logger.error("Failed to send password reset email to {}: {}", user.getEmail(), e.getMessage(), e);
            }
            return true;
        }
        return false;
    }

    // Method to confirm password reset
    @Transactional // Add Transactional annotation
    public boolean confirmPasswordReset(String token, String newPassword) {
        // Ensure UserRepository has findByResetPasswordToken(String token) method
        User user = userRepository.findByResetPasswordToken(token);
        // Ensure User model has getResetPasswordExpiry() method
        if (user != null && user.getResetPasswordExpiry() != null
                && user.getResetPasswordExpiry().isAfter(LocalDateTime.now())) {
            user.setPassword(passwordEncoder.encode(newPassword)); // Ensure User model has setPassword method
            user.setResetPasswordToken(null); // Ensure User model has setResetPasswordToken method
            user.setResetPasswordExpiry(null); // Ensure User model has setResetPasswordExpiry method
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Method to create or update student profile
    @Transactional // Add Transactional annotation
    public Student createOrUpdateStudentProfile(Long userId, Student studentProfile) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId)); // Use EntityNotFoundException
        if (studentProfile == null) {
            throw new IllegalArgumentException("Student profile data cannot be null.");
        }
        // Ensure Student model has setUser method
        studentProfile.setUser(user);
        return studentRepository.save(studentProfile);
    }

    // Method to create or update tutor profile
    @Transactional // Add Transactional annotation
    public Tutor createOrUpdateTutorProfile(Long userId, Tutor tutorProfile) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId)); // Use EntityNotFoundException
        if (tutorProfile == null) {
            throw new IllegalArgumentException("Tutor profile data cannot be null.");
        }
        // Ensure Tutor model has setUser method
        tutorProfile.setUser(user);
        return tutorRepository.save(tutorProfile);
    }

    // Add method to get student profile by user ID
    public Optional<Student> getStudentProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        // Assuming Student entity has a ManyToOne relationship with User
        // and StudentRepository has findByUser method
        return studentRepository.findByUser(user); // You might need findByUserId if relationship is mapped differently
    }

    // Add method to get tutor profile by user ID
    public Optional<Tutor> getTutorProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        // Assuming Tutor entity has a ManyToOne relationship with User
        // and TutorRepository has findByUser method
        return tutorRepository.findByUser(user); // You might need findByUserId if relationship is mapped differently
    }

    // Method to update user details
    @Transactional // Add Transactional annotation
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            // Add validation for updatedUser fields if necessary
            // For example, validate email format if email is being updated
            // You might want to update specific fields from updatedUser to existingUser

            return userRepository.save(existingUser);
        } else {
            throw new EntityNotFoundException("User not found with ID: " + id); // Use EntityNotFoundException
        }
    }

    // Method to get notifications for a user by user ID
    public Page<Notification> getNotificationsByUserId(Long userId, Pageable pageable) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            // Corrected method call to match NotificationRepository method name
            return notificationRepository.findByUser_IdOrderByCreatedAtDesc(userId, pageable);
        }
        return Page.empty(pageable); // Return an empty page if user not found
    }

    // Method to get all users with pagination
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // Method to mark a notification as read
    @Transactional // Add Transactional annotation
    public Notification markNotificationAsRead(Long notificationId, Long userId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found with ID: " + notificationId)); // Use EntityNotFoundException

        // Ensure Notification model has getUser() method
        if (!notification.getUser().getId().equals(userId)) {
            throw new IllegalStateException("User is not the owner of this notification.");
        }
        // Ensure Notification model has setReadStatus method
        notification.setReadStatus(true);
        return notificationRepository.save(notification);
    }

    // Method to count the number of students
    // Ensure UserRepository has countByRole(String role) method
    public long countStudents() {
        return userRepository.countByRole("STUDENT");
    }

    // Method to count the number of tutors
    // Ensure UserRepository has countByRole(String role) method
    public long countTutors() {
        return userRepository.countByRole("TUTOR");
    }

    // You will likely need these methods in your UserRepository:
    // User findByUsername(String username);
    // User findByEmail(String email);
    // User findByResetPasswordToken(String token);
    // long countByRole(String role);

    // You will also need to ensure your User model has:
    // - Getter and setter methods for username, email, password, role, firstName, lastName
    // - Getter and setter methods for resetPasswordToken and resetPasswordExpiry
    // - A method to get roles (e.g., getRoles() which returns a collection like Set<String>)

    // You will need to ensure your Notification model has:
    // - Getter for user (getUser())
    // - Setter for readStatus (setReadStatus(boolean))

    // You will need to ensure your NotificationRepository has:
    // - Page<Notification> findByUser_IdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    // You will need to ensure your StudentRepository has:
    // - Optional<Student> findByUser(User user); or findByUserId(Long userId);

    // You will need to ensure your TutorRepository has:
    // - Optional<Tutor> findByUser(User user); or findByUserId(Long userId);
}
