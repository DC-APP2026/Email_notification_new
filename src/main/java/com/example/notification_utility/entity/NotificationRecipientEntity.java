package com.example.notification_utility.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notification_recipients")
public class NotificationRecipientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notification_request_id", nullable = false)
    private Long notificationRequestId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "recipient_type", nullable = false)
    private String recipientType;

    public NotificationRecipientEntity() {
    }

    public Long getId() {
        return id;
    }

    public Long getNotificationRequestId() {
        return notificationRequestId;
    }

    public void setNotificationRequestId(Long notificationRequestId) {
        this.notificationRequestId = notificationRequestId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }
}