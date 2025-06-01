package com.studycircle.studycircle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}