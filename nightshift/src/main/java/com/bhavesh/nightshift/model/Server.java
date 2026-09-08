package com.bhavesh.nightshift.model;

import java.time.LocalDateTime;

public record Server(
        Long id,
        String hostname,
        String environment,
        String status,
        LocalDateTime createdAt
) {
}