package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// Create a Spring Data JPA repository interface named ResourceRepository in the package com.studycircle.studycircle.repository. It should extend JpaRepository and be for the Resource entity with a Long ID.
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Page<Resource> findAll(Pageable pageable);
    Page<Resource> findByUserId(Long userId, Pageable pageable);
    Page<Resource> findBySessionId(Long sessionId, Pageable pageable);
}