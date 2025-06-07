package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Resource;
import com.studycircle.studycircle.model.User; // Import User
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Import Repository

import java.util.List;

// Create a Spring Data JPA repository interface named ResourceRepository in the package com.studycircle.studycircle.repository. It should extend JpaRepository and be for the Resource entity with a Long ID.
@Repository // Add Repository annotation
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Page<Resource> findAll(Pageable pageable);
    List<Resource> findBySessionId(Long sessionId);
    Page<Resource> findBySessionId(Long sessionId, Pageable pageable);

    // Add the methods used in ResourceService

    // Corrected method name to match the field name 'uploader' in the Resource model
    Page<Resource> findByUploader(User uploader, Pageable pageable);
    // findBySessionId is already declared in your provided code
}
