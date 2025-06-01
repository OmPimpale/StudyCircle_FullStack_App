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

    private String fileName;

    private String fileType;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "uploader_id")
    private User uploader;

    @CreationTimestamp
    private java.time.LocalDateTime uploadTimestamp;

 @ManyToOne
 @JoinColumn(name = "session_id")
 private Session session;
}