package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Resource;
import com.studycircle.studycircle.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Resource addResource(Resource resource) {
 return resourceRepository.save(resource);
    }

    public List<Resource> findAllResources() {
 return resourceRepository.findAll();
    }
}
}