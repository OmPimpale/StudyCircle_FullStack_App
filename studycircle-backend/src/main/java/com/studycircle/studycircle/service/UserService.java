package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.User;
import com.studycircle.studycircle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService; // Inject EmailService

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
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

    public User registerUser(User user) {
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(id);
    
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
    
            // Update allowed fields
            if (updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()) {
                existingUser.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getFirstName() != null && !updatedUser.getFirstName().isEmpty()) {
                existingUser.setFirstName(updatedUser.getFirstName());
            }
            if (updatedUser.getLastName() != null && !updatedUser.getLastName().isEmpty()) {
                existingUser.setLastName(updatedUser.getLastName());
            }
            // Note: Password updates should be handled separately for security
    
            return userRepository.save(existingUser);
        } else {
            return null; // User not found
        }
    }    

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false; // User not found
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

            // Send email with reset link
            String resetLink = "YOUR_FRONTEND_RESET_PASSWORD_URL?token=" + token; // Replace with your frontend URL
            emailService.sendEmail(
                user.getEmail(),
                "Password Reset Request for StudyCircle",
                "To reset your password, click on the following link: " + resetLink + "\n\nThis link will expire in 24 hours."
            );

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
}
