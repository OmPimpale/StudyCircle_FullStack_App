package com.studycircle.studycircle.util;

import com.studycircle.studycircle.model.*; // Import all your models
import com.studycircle.studycircle.repository.*; // Import all your repositories
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal; // Import BigDecimal
import java.time.LocalDateTime;
import java.util.HashSet; // Import HashSet
import java.util.Set; // Import Set
import java.util.Optional; // Import Optional

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            StudentRepository studentRepository,
            TutorRepository tutorRepository,
            SubjectRepository subjectRepository,
            SessionRepository sessionRepository,
            BookingRepository bookingRepository,
            ResourceRepository resourceRepository,
            NotificationRepository notificationRepository) { // Include all repositories

        return args -> {
            // Clear existing data (optional, for clean testing)
            // bookingRepository.deleteAll();
            // sessionRepository.deleteAll();
            // resourceRepository.deleteAll();
            // notificationRepository.deleteAll();
            // studentRepository.deleteAll();
            // tutorRepository.deleteAll();
            // userRepository.deleteAll();
            // subjectRepository.deleteAll();

            // Create or find Users FIRST
            User studentUser;
            Optional<User> existingStudentUser = userRepository.findByUsername("student@example.com");
            if (existingStudentUser.isPresent()) {
                studentUser = existingStudentUser.get();
            } else {
                User newUser = new User();
                newUser.setUsername("student@example.com");
                newUser.setEmail("student@example.com");
                newUser.setPassword(passwordEncoder.encode("password"));
                newUser.setRole("STUDENT");
                newUser.setFirstName("Stu");
                newUser.setLastName("Dent");
                studentUser = userRepository.save(newUser);
            }

            User tutorUser;
            Optional<User> existingTutorUser = userRepository.findByUsername("tutor@example.com");
            if (existingTutorUser.isPresent()) {
                tutorUser = existingTutorUser.get();
            } else {
                User newUser = new User();
                newUser.setUsername("tutor@example.com");
                newUser.setEmail("tutor@example.com");
                newUser.setPassword(passwordEncoder.encode("password"));
                newUser.setRole("TUTOR");
                newUser.setFirstName("Tu");
                newUser.setLastName("Tor");
                tutorUser = userRepository.save(newUser);
            }

            // NOW Create or find Student and Tutor profiles using the User objects
            // Check if student profile exists for studentUser
            Student student;
            Optional<Student> existingStudent = studentRepository.findByUser(studentUser);
            if (existingStudent.isPresent()) {
                student = existingStudent.get();
            } else {
                Student newStudent = new Student();
                newStudent.setUser(studentUser);
                newStudent.setAcademicLevel("University");
                newStudent.setInterests("Math, Science");
                student = studentRepository.save(newStudent);
            }

            // Check if tutor profile exists for tutorUser
            Tutor tutor;
            Optional<Tutor> existingTutor = tutorRepository.findByUser(tutorUser);
            if (existingTutor.isPresent()) {
                tutor = existingTutor.get();
            } else {
                Tutor newTutor = new Tutor();
                newTutor.setUser(tutorUser);
                newTutor.setQualifications("PhD in Math");
                newTutor.setExperience("5 years teaching");
                newTutor.setBio("Experienced math tutor");
                newTutor.setProfilePictureUrl("http://example.com/tutor-pic.jpg");
                newTutor.setSubjectsTaught("Math, Physics");
                tutor = tutorRepository.save(newTutor);
            }

            // Create Subjects (if not created above or in other seeders)
            // Use findByName with Optional handling (already present in your code)
            subjectRepository.findByName("Math").orElseGet(() -> {
                Subject newSubject = new Subject();
                newSubject.setName("Math");
                return subjectRepository.save(newSubject);
            });
            subjectRepository.findByName("Science").orElseGet(() -> {
                Subject newSubject = new Subject();
                newSubject.setName("Science");
                return subjectRepository.save(newSubject);
            });
            subjectRepository.findByName("History").orElseGet(() -> {
                Subject newSubject = new Subject();
                newSubject.setName("History");
                return subjectRepository.save(newSubject);
            });

            // Create Sessions (consider checking for existing sessions if needed)
            // For simplicity, let's assume sessions are created every time for now,
            // but in a real app, you might want to check if a similar session exists.
            Session session1 = new Session();
            session1.setTutor(tutor);
            session1.setSubject("Math"); // Set subject name as string
            session1.setStartTime(LocalDateTime.now().plusHours(1));
            session1.setEndTime(LocalDateTime.now().plusHours(2));
            session1.setStatus(SessionStatus.SCHEDULED.name());
            session1.setHourlyRate(BigDecimal.valueOf(50.00));
            session1 = sessionRepository.save(session1);

            Session session2 = new Session();
            session2.setTutor(tutor);
            session2.setSubject("Physics"); // Set subject name as string
            session2.setStartTime(LocalDateTime.now().plusDays(1).plusHours(10));
            session2.setEndTime(LocalDateTime.now().plusDays(1).plusHours(11));
            session2.setStatus(SessionStatus.SCHEDULED.name());
            session2.setHourlyRate(BigDecimal.valueOf(60.00));
            sessionRepository.save(session2);

            // Create a Booking (consider checking for existing bookings if needed)
            // For simplicity, let's assume a booking is created every time for now.
            Booking booking1 = new Booking();
            booking1.setSession(session1);
            booking1.setStudent(student);
            booking1.setTutor(tutor);
            booking1.setStatus(BookingStatus.BOOKED.name());
            booking1.setBookingTime(LocalDateTime.now()); // Set bookingTime (corrected from previous error)
            bookingRepository.save(booking1);

            // Create a Resource (consider checking for existing resources if needed)
            // For simplicity, let's assume resources are created every time for now.
            Resource resource1 = new Resource();
            resource1.setFileName("math_notes.pdf");
            resource1.setFileType("application/pdf");
            resource1.setUrl("/uploads/math_notes.pdf"); // Dummy URL
            resource1.setUploader(tutorUser);
            resource1.setUploadTimestamp(LocalDateTime.now());
            resource1.setSession(session1);
            resourceRepository.save(resource1);

            // Create Notifications (consider checking for existing notifications if needed)
            // For simplicity, let's assume notifications are created every time for now.
            Notification notification1 = new Notification();
            notification1.setUser(studentUser);
            notification1.setMessage("Your session with Tutor Tor is scheduled.");
            notification1.setReadStatus(false);
            notification1.setCreatedAt(LocalDateTime.now());
            notificationRepository.save(notification1);

            Notification notification2 = new Notification();
            notification2.setUser(tutorUser);
            notification2.setMessage("You have a new booking from Stu Dent.");
            notification2.setReadStatus(false);
            notification2.setCreatedAt(LocalDateTime.now());
            notificationRepository.save(notification2);

            // Add more dummy data as needed for other entities and scenarios
        };
    }
}
