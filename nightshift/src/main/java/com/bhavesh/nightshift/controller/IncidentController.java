package com.bhavesh.nightshift.controller;

import com.bhavesh.nightshift.dto.CreateIncidentRequest;
import com.bhavesh.nightshift.model.Incident;
import com.bhavesh.nightshift.service.IncidentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(
            IncidentService incidentService
    ) {
        this.incidentService = incidentService;
    }

    @PostMapping("/api/servers/{serverId}/incidents")
    @ResponseStatus(HttpStatus.CREATED)
    public Incident createIncident(
            @PathVariable long serverId,
            @Valid @RequestBody CreateIncidentRequest request
    ) {
        return incidentService.openIncident(
                serverId,
                request
        );
    }

    @PatchMapping("/api/incidents/{incidentId}/resolve")
    public Incident resolveIncident(
            @PathVariable long incidentId
    ) {
        return incidentService.resolveIncident(
                incidentId
        );
    }

    @GetMapping("/api/incidents/open")
    public List<Incident> getOpenIncidents() {
        return incidentService.getOpenIncidents();
    }
}
