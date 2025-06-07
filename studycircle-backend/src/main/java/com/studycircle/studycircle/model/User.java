package com.studycircle.studycircle.model;

import jakarta.persistence.*;
// Remove Lombok annotations
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.Collection; // Import Collection
import java.util.Collections; // Import Collections

@Entity
// Remove Lombok annotations
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
@Table(name = "users") // It's good practice to explicitly name the table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // e.g., "STUDENT", "TUTOR"

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column
    private String resetPasswordToken;

    @Column
    private LocalDateTime resetPasswordExpiry;

    // Manual Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public LocalDateTime getResetPasswordExpiry() {
        return resetPasswordExpiry;
    }

    public void setResetPasswordExpiry(LocalDateTime resetPasswordExpiry) {
        this.resetPasswordExpiry = resetPasswordExpiry;
    }

    // Method to get roles (for security context)
    public Collection<String> getRoles() {
        // Assuming the 'role' field stores a single role string like "ROLE_STUDENT" or "ROLE_TUTOR"
        // If your roles are stored differently (e.g., a comma-separated string or a separate roles entity),
        // you'll need to adjust this method accordingly.
        if (this.role != null && !this.role.isEmpty()) {
            return Collections.singletonList("ROLE_" + this.role.toUpperCase()); // Prefix with "ROLE_" for Spring Security
        }
        return Collections.emptyList();
    }


    // You might need to add constructors if you removed @NoArgsConstructor and @AllArgsConstructor
    public User() {
    }

    public User(Long id, String username, String password, String role, String email, String firstName, String lastName, LocalDateTime createdAt, LocalDateTime updatedAt, String resetPasswordToken, LocalDateTime resetPasswordExpiry) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.resetPasswordToken = resetPasswordToken;
        this.resetPasswordExpiry = resetPasswordExpiry;
    }
}
