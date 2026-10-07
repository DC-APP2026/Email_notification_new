package com.example.notification_utility.repository;

import com.example.notification_utility.entity.NotificationRecipientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRecipientRepository
        extends JpaRepository<NotificationRecipientEntity, Long> {
}