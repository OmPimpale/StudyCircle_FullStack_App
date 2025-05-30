package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.User;
import com.studycircle.studycircle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    // Basic method to find a user by username (implementation to follow)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // TODO: Add other user-related methods (e.g., save, delete, update)

    public User registerUser(User user) {
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setEmail(updatedUser.getEmail());
            // Add other fields to update as needed (e.g., firstName, lastName)
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


}