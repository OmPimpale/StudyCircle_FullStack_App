package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
 User findByUsername(String username);
 User findByResetPasswordToken(String resetPasswordToken);
}