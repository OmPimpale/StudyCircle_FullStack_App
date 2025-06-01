package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.model.Tutor;
import com.studycircle.studycircle.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tutors")
public class TutorController {

    private final TutorService tutorService;

    @Autowired
    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    // Add your controller methods here
    @GetMapping
    public ResponseEntity<List<Tutor>> getAllTutors() {
        List<Tutor> tutors = tutorService.findAllTutors();
        return new ResponseEntity<>(tutors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutor> getTutorById(@PathVariable Long id) {
        return tutorService.findTutorById(id)
                .map(tutor -> new ResponseEntity<>(tutor, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tutor> updateTutor(@PathVariable Long id, @RequestBody Tutor updatedTutor) {
        Tutor tutor = tutorService.updateTutor(id, updatedTutor);
        if (tutor != null) {
            return new ResponseEntity<>(tutor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/subject")
    public ResponseEntity<List<Tutor>> getTutorsBySubjectName(@RequestParam("subjectName") String subjectName) {
        List<Tutor> tutors = tutorService.findTutorsBySubjectName(subjectName);
        if (tutors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Or HttpStatus.OK with an empty list
        }
        return new ResponseEntity<>(tutors, HttpStatus.OK);
    }
    // Add other controller methods as needed
}