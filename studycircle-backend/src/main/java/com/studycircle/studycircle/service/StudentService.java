package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Student;
import com.studycircle.studycircle.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException; // Import EntityNotFoundException
import org.springframework.transaction.annotation.Transactional; // Import Transactional

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Add service methods here
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Transactional // Add Transactional annotation
    public Student updateStudent(Long id, Student updatedStudent) {
        Optional<Student> existingStudentOptional = studentRepository.findById(id);
        if (existingStudentOptional.isPresent()) {
            Student existingStudent = existingStudentOptional.get();

            // Update relevant fields
            // Ensure Student model has getAcademicLevel and setAcademicLevel methods
            if (updatedStudent.getAcademicLevel() != null && !updatedStudent.getAcademicLevel().isEmpty()) {
                existingStudent.setAcademicLevel(updatedStudent.getAcademicLevel());
            }
            // Ensure Student model has getInterests and setInterests methods
            if (updatedStudent.getInterests() != null && !updatedStudent.getInterests().isEmpty()) {
                existingStudent.setInterests(updatedStudent.getInterests());
            }
            // Update relationship with User if needed (consider carefully)
            // Ensure Student model has getUser and setUser methods
            // if (updatedStudent.getUser() != null) {
            //     existingStudent.setUser(updatedStudent.getUser());
            // }

            return studentRepository.save(existingStudent);
        } else {
            throw new EntityNotFoundException("Student not found with ID: " + id); // Throw exception if not found
        }
    }

    // You might want to add other methods here, e.g., for creating a new student
    // @Transactional
    // public Student createStudent(Student student) {
    //     return studentRepository.save(student);
    // }
}
