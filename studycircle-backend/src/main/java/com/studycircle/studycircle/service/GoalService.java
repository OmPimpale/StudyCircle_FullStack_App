package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Goal;
import com.studycircle.studycircle.repository.GoalRepository;
import com.studycircle.studycircle.repository.UserRepository; // Assuming a UserRepository
import com.studycircle.studycircle.model.User; // Assuming a User model
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class GoalService {

    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
 this.userRepository = userRepository;
        this.goalRepository = goalRepository;
    }

    @Autowired // Autowire UserRepository or StudentRepository
    private UserRepository userRepository; // Or StudentRepository
    // Add service methods here

    public Goal addGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public List<Goal> findGoalsByStudentId(Long studentId) {
        return goalRepository.findByStudentId(studentId);
    }

    public List<Goal> findGoalsByUsername(String username) {
        // Find the user by username
        User user = userRepository.findByUsername(username); // Assuming findByUsername method

        if (user != null) {
            // Assuming User model has a method to get student ID or the User ID is the student ID
            return goalRepository.findByStudentId(user.getId()); // Or user.getStudentId()
        }
        return List.of(); // Return empty list if user not found
    }
}