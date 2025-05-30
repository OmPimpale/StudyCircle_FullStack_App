package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByStudentId(Long studentId);
}