package com.assignment1.librarymanagement.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> healthCheck() {

        log.info("Health check requested");

        return Map.of(
                "status", "UP",
                "application", "LibraryInfoDisplay",
                "timestamp", LocalDateTime.now(),
                "message", "Application is running successfully"
        );
    }
}