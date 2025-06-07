package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Subject;
import com.studycircle.studycircle.repository.SubjectRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import Transactional


@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    // Add service methods for Subject-related operations here

    @Transactional // Add Transactional annotation
    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    // Add method to find all subjects
    public List<Subject> findAllSubjects() {
        return subjectRepository.findAll();
    }

    // You might also want to add methods for getting a subject by ID, updating a subject, deleting a subject, etc.
}
