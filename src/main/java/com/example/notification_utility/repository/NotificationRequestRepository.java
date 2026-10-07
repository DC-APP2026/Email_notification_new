package com.example.notification_utility.repository;

import com.example.notification_utility.entity.NotificationRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRequestRepository
        extends JpaRepository<NotificationRequestEntity, Long> {
}