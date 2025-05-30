package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.model.Resource;
import com.studycircle.studycircle.service.ResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    // Add controller methods here

    @PostMapping
    public ResponseEntity<Resource> addResource(@RequestBody Resource resource) {
        Resource createdResource = resourceService.addResource(resource);
        return new ResponseEntity<>(createdResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getAllResources() {
        List<Resource> resources = resourceService.findAllResources();
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

}