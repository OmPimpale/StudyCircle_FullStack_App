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


            // Create Users
            User studentUser = new User();
            studentUser.setUsername("student@example.com");
            studentUser.setEmail("student@example.com");
            studentUser.setPassword(passwordEncoder.encode("password"));
            studentUser.setRole("STUDENT");
            studentUser.setFirstName("Stu");
            studentUser.setLastName("Dent");
            studentUser = userRepository.save(studentUser);

            User tutorUser = new User();
            tutorUser.setUsername("tutor@example.com");
            tutorUser.setEmail("tutor@example.com");
            tutorUser.setPassword(passwordEncoder.encode("password"));
            tutorUser.setRole("TUTOR");
            tutorUser.setFirstName("Tu");
            tutorUser.setLastName("Tor");
            tutorUser = userRepository.save(tutorUser);

            // Create Student and Tutor profiles
            Student student = new Student();
            student.setUser(studentUser);
            student.setAcademicLevel("University");
            student.setInterests("Math, Science");
            studentRepository.save(student);

            Tutor tutor = new Tutor();
            tutor.setUser(tutorUser);
            tutor.setQualifications("PhD in Math");
            tutor.setExperience("5 years teaching");
            tutor.setBio("Experienced math tutor");
            tutor.setProfilePictureUrl("http://example.com/tutor-pic.jpg");

            // Set subjects taught as a string to match Tutor model
            tutor.setSubjectsTaught("Math, Physics"); // Corrected to use subjectsTaught field

            tutorRepository.save(tutor);


            // Create Subjects (if not created above or in other seeders)
            // Use findByName with Optional handling
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


            // Create Sessions
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

            // Create a Booking
            Booking booking1 = new Booking();
            booking1.setSession(session1);
            booking1.setStudent(student);
            booking1.setTutor(tutor);
            booking1.setStatus(BookingStatus.BOOKED.name());
             // booking1.setBookingDate(LocalDateTime.now());
            bookingRepository.save(booking1);

            // Create a Resource
            Resource resource1 = new Resource();
            resource1.setFileName("math_notes.pdf");
            resource1.setFileType("application/pdf");
            resource1.setUrl("/uploads/math_notes.pdf"); // Dummy URL
            resource1.setUploader(tutorUser);
            resource1.setUploadTimestamp(LocalDateTime.now());
            resource1.setSession(session1);
            resourceRepository.save(resource1);

            // Create a Notification
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
