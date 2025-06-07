package com.studycircle.studycircle.controller;

import jakarta.persistence.EntityNotFoundException;
import com.studycircle.studycircle.exception.ErrorResponse; // Import ErrorResponse
import com.studycircle.studycircle.model.Resource;
import com.studycircle.studycircle.service.ResourceService;
import com.studycircle.studycircle.model.User; // Import User
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal; // Import Principal
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.data.domain.Page; // Import Page
import org.springframework.data.domain.Pageable; // Import Pageable


@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Resource> uploadResource(
            @RequestParam("file") MultipartFile file,
            @RequestParam("sessionId") Long sessionId, // Add sessionId as a request parameter
            Principal principal) {
        // Assuming the user ID is available from the authenticated user's principal
        Long userId = Long.parseLong(principal.getName()); // Or get user ID from principal in a different way depending on your security config
        Resource uploadedResource;
        try {
            uploadedResource = resourceService.uploadResource(file, userId, sessionId); // Pass sessionId to service
        } catch (Exception e) { // Catching a general Exception for now, consider more specific exceptions
             // Log the error: logger.error("Error uploading resource", e);
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Or a more specific error response
        }
        return new ResponseEntity<>(uploadedResource, HttpStatus.CREATED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable Long id) {
        Optional<Resource> resource = resourceService.getResourceById(id);
        return resource.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Resource>> getAllResourcesByUserId(@PathVariable Long userId, Pageable pageable) {
        Page<Resource> resources = resourceService.getResourcesByUserId(userId, pageable);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping("/session/{sessionId}/resources") // Corrected endpoint path
    public ResponseEntity<Page<Resource>> getResourcesBySessionId(@PathVariable Long sessionId, Pageable pageable) {
        Page<Resource> resources = resourceService.getResourcesBySessionId(sessionId, pageable);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Resource>> getAllResources(Pageable pageable) {
        Page<Resource> resources = resourceService.getAllResources(pageable);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resource> updateResource(@PathVariable Long id,
                                                  @RequestParam("file") MultipartFile file, // Accept MultipartFile for update
                                                  Principal principal) {
        Long userId = Long.parseLong(principal.getName()); // Or get user ID from principal
        try {
            Resource updatedResource = resourceService.updateResource(id, file, userId); // Pass MultipartFile to service
            return new ResponseEntity<>(updatedResource, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Or HttpStatus.CONFLICT depending on the exact meaning
        } catch (AccessDeniedException ex) { // Catch AccessDeniedException
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception ex) { // Catch other potential exceptions during file handling
             // Log the error: logger.error("Error updating resource", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id, Principal principal) {
        Long userId = Long.parseLong(principal.getName()); // Or get user ID from principal
        try {
            resourceService.deleteResource(id, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Or HttpStatus.CONFLICT
        } catch (AccessDeniedException ex) { // Catch AccessDeniedException
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception ex) { // Catch other potential exceptions during file handling
             // Log the error: logger.error("Error deleting resource", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
