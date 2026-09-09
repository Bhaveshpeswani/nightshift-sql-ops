package com.bhavesh.nightshift.model;

import java.time.LocalDateTime;

public record Incident(
        Long id,
        Long serverId,
        String title,
        String status,
        LocalDateTime openedAt,
        LocalDateTime resolvedAt
) {
}
