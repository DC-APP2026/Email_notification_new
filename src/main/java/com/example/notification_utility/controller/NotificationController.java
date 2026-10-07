package com.example.notification_utility.controller;

import com.example.notification_utility.dto.EmailRequest;
import com.example.notification_utility.dto.NotificationRequest;
import com.example.notification_utility.service.EmailService;
import com.example.notification_utility.service.NotificationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final EmailService emailService;
    private final NotificationService notificationService;

    public NotificationController(
            EmailService emailService,
            NotificationService notificationService) {

        this.emailService = emailService;
        this.notificationService = notificationService;
    }

    // Simple email endpoint
    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(
            @RequestBody EmailRequest request) {

        emailService.sendEmail(
                request.getRecipient(),
                request.getSubject(),
                request.getBody()
        );

        return ResponseEntity.ok("Email sent successfully");
    }

    // Generic notification endpoint
    @PostMapping
    public ResponseEntity<String> sendNotification(
            @RequestBody NotificationRequest request) {

        try {

            notificationService.sendNotification(request);

            return ResponseEntity.ok(
                    "Notification sent successfully");

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity.internalServerError()
                    .body("Failed to send notification");
        }
    }
}