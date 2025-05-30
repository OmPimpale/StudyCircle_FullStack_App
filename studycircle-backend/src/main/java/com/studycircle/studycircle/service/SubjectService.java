package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Subject;
import com.studycircle.studycircle.repository.SubjectRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    // Add service methods for Subject-related operations here

    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }
}