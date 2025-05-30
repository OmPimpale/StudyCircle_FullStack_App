package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Goal;
import com.studycircle.studycircle.repository.GoalRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GoalService {

    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    // Add service methods here

    public Goal addGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public List<Goal> findGoalsByStudentId(Long studentId) {
        return goalRepository.findByStudentId(studentId);
    }
}