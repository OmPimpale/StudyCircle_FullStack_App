package com.studycircle.studycircle.model;

import jakarta.persistence.*;
// Remove Lombok annotations
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

import java.util.Set;

@Entity
// Remove Lombok annotations
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subjectsTaught;

    private double hourlyRate;

    private String qualifications;

    private String experience;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String bio;

    private String profilePictureUrl;

    // Manual Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectsTaught() {
        return subjectsTaught;
    }

    public void setSubjectsTaught(String subjectsTaught) {
        this.subjectsTaught = subjectsTaught;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    // You might need to add constructors if you removed @NoArgsConstructor and @AllArgsConstructor
    public Tutor() {
    }

    public Tutor(Long id, String subjectsTaught, double hourlyRate, String qualifications, String experience, User user, String bio, String profilePictureUrl) {
        this.id = id;
        this.subjectsTaught = subjectsTaught;
        this.hourlyRate = hourlyRate;
        this.qualifications = qualifications;
        this.experience = experience;
        this.user = user;
        this.bio = bio;
        this.profilePictureUrl = profilePictureUrl;
    }
}
