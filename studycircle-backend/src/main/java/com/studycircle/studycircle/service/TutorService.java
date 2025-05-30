package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Tutor;
import com.studycircle.studycircle.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;

    @Autowired
    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    // Add methods for tutor-related business logic here

    public List<Tutor> findAllTutors() {
        return tutorRepository.findAll();
    }
    public Optional<Tutor> findTutorById(Long id) {
        return tutorRepository.findById(id);
    }

    public Tutor updateTutor(Long id, Tutor updatedTutor) {
        Optional<Tutor> existingTutorOptional = tutorRepository.findById(id);
        if (existingTutorOptional.isPresent()) {
            Tutor existingTutor = existingTutorOptional.get();
            // Update relevant fields from updatedTutor
            existingTutor.setBio(updatedTutor.getBio());
            return tutorRepository.save(existingTutor);
        }
        return null; // Or throw an exception
    }

    public List<Tutor> findTutorsBySubjectName(String subjectName) {
        return tutorRepository.findBySubjectsName(subjectName);
    }
}