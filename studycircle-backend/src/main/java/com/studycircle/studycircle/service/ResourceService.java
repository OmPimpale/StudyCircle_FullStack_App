package com.studycircle.studycircle.service;

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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.UUID;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.persistence.EntityNotFoundException;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository, UserRepository userRepository, SessionRepository sessionRepository) {
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }
    private final String uploadDir = "uploads"; // Directory to store uploaded files

    public Resource uploadResource(MultipartFile file, Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        if (!user.getRoles().contains("TUTOR")) {
            throw new AccessDeniedException("Only tutors can upload resources");
        }

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with ID: " + sessionId));

        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName; // Generate a unique file name
        Path filePath = Paths.get(uploadDir, uniqueFileName);
        Files.createDirectories(filePath.getParent()); // Create directory if it doesn't exist
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        Resource resource = new Resource();
        resource.setFileName(originalFileName);
        resource.setUrl(filePath.toString()); // Store the file path as the URL
        resource.setUser(user);
        resource.setSession(session);
        return resourceRepository.save(resource);
    }

    public Optional<Resource> getResourceById(Long id) {
        return resourceRepository.findById(id);
    }

 public Page<Resource> getAllResources(Pageable pageable) {
        return resourceRepository.findAll();
    }

 public Page<Resource> getResourcesByUserId(Long userId, Pageable pageable) {
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return resourceRepository.findByUser(user);
    }

    public Resource updateResource(Long id, MultipartFile newFile, Long authenticatedUserId) throws IOException {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found with ID: " + id));

        if (!resource.getUser().getId().equals(authenticatedUserId)) {
            throw new AccessDeniedException("You do not have permission to update this resource.");
        }

        try {
            Path oldFilePath = Paths.get(resource.getUrl());
            Files.deleteIfExists(oldFilePath);
        } catch (IOException e) {
        }

        // Save the new file
        String originalFileName = newFile.getOriginalFilename();
        // For demonstration, I'll assume the user ID is passed or obtained elsewhere.
        // Long authenticatedUserId = ... // Get authenticated user ID
        // if (!resource.getUser().getId().equals(authenticatedUserId)) {
        //     throw new AccessDeniedException("You do not have permission to update this resource.");
        // }

        // Optionally delete the file from the file system
        String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;
        Path newFilePath = Paths.get(uploadDir, uniqueFileName);
        try {
            Files.copy(newFile.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store new file", e);
        }

        resource.setFileName(originalFileName);
        resource.setUrl(newFilePath.toString());
        resource.setCreatedAt(Instant.now());
        return resourceRepository.save(resource);
    }

    public void deleteResource(Long id, Long authenticatedUserId) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found with ID: " + id));

        if (!resource.getUser().getId().equals(authenticatedUserId)) {
            throw new AccessDeniedException("You do not have permission to delete this resource.");
        }

        try {
            Path filePath = Paths.get(resource.getUrl());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
        }
        resourceRepository.deleteById(id);
    }

 public Page<Resource> getResourcesBySessionId(Long sessionId, Pageable pageable) {
 return resourceRepository.findBySessionId(sessionId, pageable);
    }
}