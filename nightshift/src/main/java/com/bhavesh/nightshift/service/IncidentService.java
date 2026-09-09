package com.bhavesh.nightshift.service;

import com.bhavesh.nightshift.dto.CreateIncidentRequest;
import com.bhavesh.nightshift.model.Incident;
import com.bhavesh.nightshift.repository.IncidentRepository;
import com.bhavesh.nightshift.repository.ServerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final ServerRepository serverRepository;

    public IncidentService(
            IncidentRepository incidentRepository,
            ServerRepository serverRepository
    ) {
        this.incidentRepository = incidentRepository;
        this.serverRepository = serverRepository;
    }

    @Transactional
    public Incident openIncident(
            long serverId,
            CreateIncidentRequest request
    ) {

        serverRepository.findById(serverId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Server not found"
                        )
                );

        long incidentId =
                incidentRepository.create(serverId, request.title());

        serverRepository.updateStatus(
                serverId,
                "DEGRADED"
        );

        return getIncident(incidentId);
    }

    @Transactional
    public Incident resolveIncident(long incidentId) {

        Incident incident = getIncident(incidentId);

        if ("RESOLVED".equals(incident.status())) {
            return incident;
        }

        incidentRepository.resolve(incidentId);

        int openIncidents =
                incidentRepository.countOpenForServer(
                        incident.serverId()
                );

        if (openIncidents == 0) {
            serverRepository.updateStatus(
                    incident.serverId(),
                    "HEALTHY"
            );
        }

        return getIncident(incidentId);
    }

    public Incident getIncident(long id) {

        return incidentRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Incident not found"
                        )
                );
    }

    public List<Incident> getOpenIncidents() {
        return incidentRepository.findOpen();
    }
}
