package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.model.Goal;
import com.studycircle.studycircle.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    // Add controller methods here for handling goal-related requests

    @PostMapping
    public ResponseEntity<Goal> addGoal(@RequestBody Goal goal) {
        Goal createdGoal = goalService.addGoal(goal);
        return new ResponseEntity<>(createdGoal, HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Goal>> getGoalsByStudentId(@PathVariable Long studentId) {
        List<Goal> goals = goalService.findGoalsByStudentId(studentId);
        return new ResponseEntity<>(goals, HttpStatus.OK);
    }
}