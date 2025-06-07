package com.studycircle.studycircle.service;

import java.util.UUID;

import com.studycircle.studycircle.model.Resource;
import com.studycircle.studycircle.model.Session;
import com.studycircle.studycircle.model.User;
import com.studycircle.studycircle.repository.ResourceRepository;
import com.studycircle.studycircle.repository.SessionRepository;
import com.studycircle.studycircle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException; // Changed import to jakarta.persistence.EntityNotFoundException
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
// Change import from java.time.Instant to java.time.LocalDateTime
import java.time.LocalDateTime; // Import LocalDateTime
import java.util.Optional;
// Remove unused import: import java.util.UUID;
import java.util.Set; // Import Set if roles are stored in a Set

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional; // Import Transactional


@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository,
            UserRepository userRepository,
            SessionRepository sessionRepository) {
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    private final String uploadDir = "uploads"; // Directory to store uploaded files

    /**
     * Upload resource file associated with a user and session.
     *
     * @param file      Multipart file to upload
     * @param userId    ID of the user uploading the file
     * @param sessionId ID of the session to associate resource with
     * @return saved Resource entity
     * @throws IOException on file errors
     */
    @Transactional // Add Transactional annotation
    public Resource uploadResource(MultipartFile file, Long userId, Long sessionId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId)); // Use EntityNotFoundException

        // Ensure User model has getRoles() method that returns a Collection (like Set) of roles (Strings)
        if (!user.getRoles().contains("TUTOR")) {
            throw new AccessDeniedException("Only tutors can upload resources");
        }

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with ID: " + sessionId)); // Use EntityNotFoundException

        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName; // Generate unique file name
        Path filePath = Paths.get(uploadDir, uniqueFileName);
        Files.createDirectories(filePath.getParent()); // Create directory if it doesn't exist
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Resource resource = new Resource();
        // Ensure Resource model has setFileName, setUrl, setUploader, setSession, and setUploadTimestamp methods
        resource.setFileName(originalFileName);
        resource.setUrl(filePath.toString()); // Store file path as URL
        resource.setUploader(user); // Corrected method call
        resource.setSession(session);
        resource.setUploadTimestamp(LocalDateTime.now()); // Corrected method call and used LocalDateTime.now()

        return resourceRepository.save(resource);
    }

    public Optional<Resource> getResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    public Page<Resource> getAllResources(Pageable pageable) {
        return resourceRepository.findAll(pageable);
    }

    public Page<Resource> getResourcesByUserId(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId)); // Use EntityNotFoundException
        // Ensure ResourceRepository has findByUploader method (assuming the relationship is mapped by uploader)
        return resourceRepository.findByUploader(user, pageable); // Corrected method call
    }

    @Transactional // Add Transactional annotation
    public Resource updateResource(Long id, MultipartFile newFile, Long authenticatedUserId) throws IOException {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found with ID: " + id)); // Use EntityNotFoundException

        // Ensure Resource model has getUploader() method
        if (!resource.getUploader().getId().equals(authenticatedUserId)) { // Corrected method call
            throw new AccessDeniedException("You do not have permission to update this resource.");
        }

        // Delete old file if exists
        try {
            // Ensure Resource model has getUrl() method
            Path oldFilePath = Paths.get(resource.getUrl());
            Files.deleteIfExists(oldFilePath);
        } catch (IOException e) {
            // Log or handle as needed
            System.err.println("Failed to delete old file: " + e.getMessage());
            // Depending on your error handling strategy, you might throw a custom exception here
        }

        // Save the new file
        String originalFileName = newFile.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName; // Use UUID for uniqueness
        Path newFilePath = Paths.get(uploadDir, uniqueFileName);
        Files.createDirectories(newFilePath.getParent());
        Files.copy(newFile.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);

        // Ensure Resource model has setFileName, setUrl, and setUploadTimestamp methods
        resource.setFileName(originalFileName);
        resource.setUrl(newFilePath.toString());
        resource.setUploadTimestamp(LocalDateTime.now()); // Corrected method call and used LocalDateTime.now()

        return resourceRepository.save(resource);
    }

    @Transactional // Add Transactional annotation
    public void deleteResource(Long id, Long authenticatedUserId) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found with ID: " + id)); // Use EntityNotFoundException

        // Ensure Resource model has getUploader() method
        if (!resource.getUploader().getId().equals(authenticatedUserId)) { // Corrected method call
            throw new AccessDeniedException("You do not have permission to delete this resource.");
        }

        // Delete file from file system
        try {
            // Ensure Resource model has getUrl() method
            Path filePath = Paths.get(resource.getUrl());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Log or handle as needed
            System.err.println("Failed to delete file: " + e.getMessage());
            // Depending on your error handling strategy, you might throw a custom exception here
        }

        resourceRepository.deleteById(id);
    }

    public Page<Resource> getResourcesBySessionId(Long sessionId, Pageable pageable) {
        // Ensure ResourceRepository has findBySessionId method
        return resourceRepository.findBySessionId(sessionId, pageable);
    }

    // You will likely need these methods in your ResourceRepository:
    // Page<Resource> findByUploader(User uploader, Pageable pageable);
    // Page<Resource> findBySessionId(Long sessionId, Pageable pageable);
    // List<Resource> findBySessionId(Long sessionId); // If you need a List version


    // You need to ensure your User model has a method like getRoles()
    // that returns a collection of roles (e.g., Set<String>).
}
