package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.model.Subject;
import com.studycircle.studycircle.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;


    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // Add API endpoints related to subjects here

    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects() {
        List<Subject> subjects = subjectService.findAllSubjects();
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    // This endpoint requires admin privileges
    @PostMapping
    public ResponseEntity<Subject> addSubject(@RequestBody Subject subject) {
        Subject createdSubject = subjectService.addSubject(subject);
 return new ResponseEntity<>(createdSubject, HttpStatus.CREATED);
    }

}