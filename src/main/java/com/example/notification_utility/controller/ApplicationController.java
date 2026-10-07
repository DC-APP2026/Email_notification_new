package com.example.notification_utility.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.notification_utility.entity.Application;
import com.example.notification_utility.service.ApplicationService;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<Application> registerApplication(
            @RequestParam String applicationCode,
            @RequestParam String applicationName) {

        Application application =
                applicationService.registerApplication(
                        applicationCode,
                        applicationName
                );

        return ResponseEntity.ok(application);
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {

        return ResponseEntity.ok(
                applicationService.getAllApplications()
        );
    }
}