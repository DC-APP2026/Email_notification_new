package com.example.notification_utility.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.notification_utility.entity.Application;

public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

    Optional<Application> findByApplicationCode(String applicationCode);

    Optional<Application> findByApplicationCodeAndActiveTrue(
            String applicationCode);
}