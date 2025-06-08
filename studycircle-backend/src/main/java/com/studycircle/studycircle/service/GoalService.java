package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Goal;
import com.studycircle.studycircle.repository.GoalRepository;
import com.studycircle.studycircle.repository.UserRepository; // Assuming a UserRepository
import com.studycircle.studycircle.model.User; // Assuming a User model
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional; // Import Transactional

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository; // Use constructor injection

    @Autowired // Use constructor injection
    public GoalService(GoalRepository goalRepository, UserRepository userRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    // Add service methods here

    @Transactional // Add Transactional annotation
    public Goal addGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    // Assuming GoalRepository has findByStudentId method
    public List<Goal> findGoalsByStudentId(Long studentId) {
        return goalRepository.findByStudentId(studentId);
    }

    // Assuming GoalRepository has findByStudentId method
    public List<Goal> findGoalsByUsername(String username) {
        // Find the user by username
        // Ensure UserRepository has findByUsername(String username) method that returns
        // Optional<User>
        User user = userRepository.findByUsername(username).orElse(null);

        if (user != null) {
            // Assuming User model has a method to get ID (getId())
            return goalRepository.findByStudentId(user.getId());
        }
        return List.of(); // Return empty list if user not found
    }

    // You might want to add other methods here, e.g., to get goals by ID, update
    // goals, delete goals
}
