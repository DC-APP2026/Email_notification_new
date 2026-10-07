package com.example.notification_utility.service;

import com.example.notification_utility.entity.Application;
import com.example.notification_utility.repository.ApplicationRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public Application registerApplication(
            String applicationCode,
            String applicationName) {

        if (applicationRepository
                .findByApplicationCode(applicationCode)
                .isPresent()) {

            throw new RuntimeException(
                    "Application already registered: "
                            + applicationCode);
        }

        Application application = new Application();

        application.setApplicationCode(applicationCode);
        application.setApplicationName(applicationName);
        application.setActive(true);
        application.setCreatedAt(LocalDateTime.now());

        return applicationRepository.save(application);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public boolean isApplicationActive(String applicationCode) {

        return applicationRepository
                .findByApplicationCodeAndActiveTrue(applicationCode)
                .isPresent();
    }
}