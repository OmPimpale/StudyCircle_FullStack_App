package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}