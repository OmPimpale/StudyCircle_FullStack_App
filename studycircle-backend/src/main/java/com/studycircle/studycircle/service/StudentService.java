package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Student;
import com.studycircle.studycircle.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Student updateStudent(Long id, Student updatedStudent) {
        Optional<Student> existingStudentOptional = studentRepository.findById(id);
        if (existingStudentOptional.isPresent()) {
            Student existingStudent = existingStudentOptional.get();

            // Update relevant fields
            if (updatedStudent.getAcademicLevel() != null && !updatedStudent.getAcademicLevel().isEmpty()) {
                existingStudent.setAcademicLevel(updatedStudent.getAcademicLevel());
            }
            if (updatedStudent.getInterests() != null && !updatedStudent.getInterests().isEmpty()) {
                existingStudent.setInterests(updatedStudent.getInterests());
            }
            // Update relationship with User if needed (consider carefully)
            // if (updatedStudent.getUser() != null) {
            //     existingStudent.setUser(updatedStudent.getUser());
            // }

            return studentRepository.save(existingStudent);
        }
        return null; // Or throw an exception
    }
}