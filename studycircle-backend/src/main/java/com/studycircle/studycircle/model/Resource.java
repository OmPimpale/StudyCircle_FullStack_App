package com.studycircle.studycircle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String filePath;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private String resourceType; // e.g., "PDF", "Video", "Link"

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "uploader_id")
    private User uploader;

    @CreationTimestamp
    private java.time.LocalDateTime uploadTimestamp;
}