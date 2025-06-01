package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
 User findByUsername(String username);
 User findByEmail(String email);
 User findByResetPasswordToken(String resetPasswordToken);
 Page<User> findAll(Pageable pageable);
 long countByRole(String role);
}