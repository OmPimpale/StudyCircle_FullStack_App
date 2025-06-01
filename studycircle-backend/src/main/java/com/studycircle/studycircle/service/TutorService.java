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

            // Update relevant fields
            if (updatedTutor.getQualifications() != null && !updatedTutor.getQualifications().isEmpty()) {
                existingTutor.setQualifications(updatedTutor.getQualifications());
            }
            if (updatedTutor.getExperience() != null && !updatedTutor.getExperience().isEmpty()) {
                existingTutor.setExperience(updatedTutor.getExperience());
            }
            if (updatedTutor.getBio() != null && !updatedTutor.getBio().isEmpty()) {
                existingTutor.setBio(updatedTutor.getBio());
            }
            if (updatedTutor.getProfilePictureUrl() != null && !updatedUser.getProfilePictureUrl().isEmpty()) {
                existingTutor.setProfilePictureUrl(updatedUser.getProfilePictureUrl());
            }
            // Updating subjects might require a separate method or careful handling
            // if (updatedTutor.getSubjects() != null) {
            //     existingTutor.setSubjects(updatedTutor.getSubjects()); // This might need more complex logic
            // }
            // Update relationship with User if needed (consider carefully)
            // if (updatedTutor.getUser() != null) {
            //     existingTutor.setUser(updatedTutor.getUser());
            // }


            return tutorRepository.save(existingTutor);
        }
        return null; // Or throw an exception
    }


    public List<Tutor> findTutorsBySubjectName(String subjectName) {
        return tutorRepository.findBySubjectsName(subjectName);
    }
    // Add other service methods as needed
}