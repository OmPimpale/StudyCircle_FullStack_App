package com.studycircle.studycircle.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
// Remove Lombok annotations
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
import jakarta.persistence.Column;

@Entity
@Table(name = "students")
// Remove Lombok annotations
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column
    private String academicLevel;

    // Add any additional student-specific fields here in the future
    @Column // Assuming interests is a String for now
    private String interests;


    // Manual Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    // You might need to add constructors if you removed @NoArgsConstructor and @AllArgsConstructor
    public Student() {
    }

    public Student(Long id, User user, String academicLevel, String interests) {
        this.id = id;
        this.user = user;
        this.academicLevel = academicLevel;
        this.interests = interests;
    }
}
