package com.example.notification_utility.service;

import com.example.notification_utility.dto.NotificationRequest;
import com.example.notification_utility.dto.Recipient;
import com.example.notification_utility.entity.NotificationRecipientEntity;
import com.example.notification_utility.entity.NotificationRequestEntity;
import com.example.notification_utility.repository.NotificationRecipientRepository;
import com.example.notification_utility.repository.NotificationRequestRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final ApplicationService applicationService;
    private final TemplateRenderer templateRenderer;
    private final EmailService emailService;
    private final NotificationRequestRepository notificationRequestRepository;
    private final NotificationRecipientRepository notificationRecipientRepository;

    public NotificationService(
            ApplicationService applicationService,
            TemplateRenderer templateRenderer,
            EmailService emailService,
            NotificationRequestRepository notificationRequestRepository,
            NotificationRecipientRepository notificationRecipientRepository) {

        this.applicationService = applicationService;
        this.templateRenderer = templateRenderer;
        this.emailService = emailService;
        this.notificationRequestRepository =
                notificationRequestRepository;
        this.notificationRecipientRepository =
                notificationRecipientRepository;
    }

    public void sendNotification(NotificationRequest request) {

        // 1. Validate application
        if (!applicationService.isApplicationActive(
                request.getApplicationCode())) {

            throw new IllegalArgumentException(
                    "Application is not registered or inactive");
        }

        // 2. Create notification request
        NotificationRequestEntity notificationRequest =
                new NotificationRequestEntity();

        notificationRequest.setApplicationCode(
                request.getApplicationCode());

        notificationRequest.setEventCode(
                request.getEventCode());

        notificationRequest.setChannel(
                request.getChannel());

        notificationRequest.setSubject(
                request.getSubject());

        notificationRequest.setStatus("RECEIVED");

        notificationRequest.setCreatedAt(
                LocalDateTime.now());

        notificationRequest.setUpdatedAt(
                LocalDateTime.now());

        notificationRequest =
                notificationRequestRepository.save(
                        notificationRequest);

        // 3. Save recipients
        for (Recipient recipient : request.getRecipients()) {

            NotificationRecipientEntity recipientEntity =
                    new NotificationRecipientEntity();

            recipientEntity.setNotificationRequestId(
                    notificationRequest.getId());

            recipientEntity.setEmail(
                    recipient.getEmail());

            recipientEntity.setRecipientType(
                    recipient.getType());

            notificationRecipientRepository.save(
                    recipientEntity);
        }

        // 4. Process notification
        try {

            notificationRequest.setStatus("PROCESSING");

            notificationRequest.setUpdatedAt(
                    LocalDateTime.now());

            notificationRequestRepository.save(
                    notificationRequest);

            // 5. Render template
            String renderedTemplate =
                    templateRenderer.render(
                            request.getTemplate(),
                            request.getTemplateData()
                    );

            System.out.println("Rendered Template:");
            System.out.println(renderedTemplate);

            // 6. Send email
            for (Recipient recipient : request.getRecipients()) {

                if ("TO".equalsIgnoreCase(
                        recipient.getType())) {

                    emailService.sendEmail(
                            recipient.getEmail(),
                            request.getSubject(),
                            renderedTemplate
                    );
                }
            }

            // 7. Mark successful
            notificationRequest.setStatus("SUCCESS");

            notificationRequest.setUpdatedAt(
                    LocalDateTime.now());

            notificationRequestRepository.save(
                    notificationRequest);

        } catch (Exception e) {

            // 8. Mark failed
            notificationRequest.setStatus("FAILED");

            notificationRequest.setUpdatedAt(
                    LocalDateTime.now());

            notificationRequestRepository.save(
                    notificationRequest);

            throw e;
        }
    }
}