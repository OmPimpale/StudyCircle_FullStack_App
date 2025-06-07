package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.model.Tutor;
import com.studycircle.studycircle.service.TutorService;
import com.studycircle.studycircle.dto.AvailableTimeSlot; // Import AvailableTimeSlot
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
// Remove unused import: import java.util.Map;

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

    @GetMapping("/search")
    public ResponseEntity<List<Tutor>> searchTutorsBySubject(@RequestParam("subject") String subject) {
        List<Tutor> tutors = tutorService.findTutorsBySubject(subject);
        return new ResponseEntity<>(tutors, HttpStatus.OK);
    }

    @GetMapping("/{tutorId}/available-slots") // Corrected return type to match service
    public ResponseEntity<List<AvailableTimeSlot>> getAvailableSlots(@PathVariable Long tutorId, @RequestParam(value = "date", required = false) LocalDate date) {
        // Corrected method call to match service method name
        List<AvailableTimeSlot> availableSlots = tutorService.getAvailableTimeSlots(tutorId, date);
        return new ResponseEntity<>(availableSlots, HttpStatus.OK);
    }
    // Add other controller methods as needed
}
