package com.bhavesh.nightshift.model;

public record IncidentSummary(
        String hostname,
        String environment,
        String status,
        Integer openIncidents
) {
}
