package com.bhavesh.nightshift.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateServerRequest (

        @NotBlank
        String hostname,

        @NotBlank
        @Pattern(regexp = "DEV|TEST|PROD")
        String environment

) {
}
