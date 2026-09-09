package com.bhavesh.nightshift.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateIncidentRequest(
        @NotBlank
        String title
) {
}
